package com.safeway.tech.services;

import com.safeway.tech.dto.AlunoComLocalizacao;
import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.repository.ItinerarioAlunoRepository;
import com.safeway.tech.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItinerarioAlunoService {

    @Autowired
    private ItinerarioAlunoRepository itinerarioAlunoRepository;

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private EnderecoService enderecoService;

    /**
     * Adiciona um aluno a um itinerário existente
     */
    @Transactional
    public void adicionarAluno(Long itinerarioId, ItinerarioAlunoRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(itinerarioId)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        Aluno aluno = alunoService.buscarAlunoPorId(request.alunoId());

        // Determinar endereço: usar request.enderecoId() se presente, caso contrário tentar fallback
        Endereco endereco;
        if (request.enderecoId() != null) {
            endereco = enderecoService.buscarEntidade(request.enderecoId());
        } else {
            endereco = aluno.getResponsaveis().stream()
                    .map(r -> r.getEndereco())
                    .filter(e -> e != null)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Nenhum endereço disponível para o responsável do aluno"));
        }

        // Validar que o endereço tem lat/lng válidos antes de prosseguir
        if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
            throw new RuntimeException("Endereço selecionado não possui latitude/longitude válidas");
        }
        double lat = endereco.getLatitude();
        double lng = endereco.getLongitude();
        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            throw new RuntimeException("Coordenadas do endereço inválidas: " + lat + ", " + lng);
        }

        boolean enderecoPerenceAoResponsavel = aluno.getResponsaveis().stream()
                .anyMatch(r -> r.getEndereco() != null && r.getEndereco().getIdEndereco().equals(endereco.getIdEndereco()));

        if (!enderecoPerenceAoResponsavel) {
            throw new RuntimeException("Endereço não pertence a nenhum responsável do aluno");
        }

        // Evita duplicidade
        itinerarioAlunoRepository.findByItinerarioIdAndAlunoId(itinerarioId, aluno.getIdAluno())
                .ifPresent(a -> {
                    throw new RuntimeException("Aluno já está vinculado a este itinerário");
                });

        ItinerarioAluno entity = new ItinerarioAluno();
        entity.setItinerario(itinerario);
        entity.setAluno(aluno);
        entity.setEndereco(endereco);
        entity.setOrdemEmbarque(request.ordemEmbarque());

        itinerarioAlunoRepository.save(entity);
    }

    @Transactional
    public void removerAluno(Long itinerarioId, Long alunoId) {
        ItinerarioAluno entity = itinerarioAlunoRepository
                .findByItinerarioIdAndAlunoId(itinerarioId, alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado no itinerário"));

        itinerarioAlunoRepository.delete(entity);
    }

    @Transactional
    public void sincronizarAlunos(Itinerario itinerario, List<ItinerarioAlunoRequest> novos) {
        // Remove todos os vínculos anteriores
        itinerarioAlunoRepository.deleteAllByItinerarioId(itinerario.getId());

        // Cria novos vínculos — atribui endereco e valida se pertence ao responsável
        List<ItinerarioAluno> entidades = novos.stream().map(dto -> {
            ItinerarioAluno ia = new ItinerarioAluno();
            ia.setItinerario(itinerario);

            Aluno aluno = alunoService.buscarAlunoPorId(dto.alunoId());

            // Determinar endereco: prefer dto.enderecoId(), senão fallback para primeiro endereco de responsavel
            Endereco endereco;
            if (dto.enderecoId() != null) {
                endereco = enderecoService.buscarEntidade(dto.enderecoId());
            } else {
                endereco = aluno.getResponsaveis().stream()
                        .map(r -> r.getEndereco())
                        .filter(e -> e != null)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Nenhum endereço disponível para o responsável do aluno"));
            }

            // validar lat/lng
            if (endereco.getLatitude() == null || endereco.getLongitude() == null) {
                throw new RuntimeException("Endereço do aluno (id=" + aluno.getIdAluno() + ") não possui latitude/longitude válidas");
            }
            double lat = endereco.getLatitude();
            double lng = endereco.getLongitude();
            if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
                throw new RuntimeException("Coordenadas do endereço inválidas para aluno id=" + aluno.getIdAluno() + ": " + lat + ", " + lng);
            }

            boolean enderecoPerenceAoResponsavel = aluno.getResponsaveis().stream()
                    .anyMatch(r -> r.getEndereco() != null && r.getEndereco().getIdEndereco().equals(endereco.getIdEndereco()));

            if (!enderecoPerenceAoResponsavel) {
                throw new RuntimeException("Endereço não pertence a nenhum responsável do aluno");
            }

            ia.setAluno(aluno);
            ia.setEndereco(endereco);
            ia.setOrdemEmbarque(dto.ordemEmbarque());
            return ia;
        }).toList();

        itinerarioAlunoRepository.saveAll(entidades);
    }

    @Transactional
    public void reordenar(Long itinerarioId, List<Long> novaOrdemAlunoIds) {
        List<ItinerarioAluno> atuais = itinerarioAlunoRepository.findByItinerarioId(itinerarioId);

        Map<Long, ItinerarioAluno> map = atuais.stream()
                .collect(Collectors.toMap(a -> a.getAluno().getIdAluno(), a -> a));

        int ordem = 1;
        for (Long id : novaOrdemAlunoIds) {
            ItinerarioAluno ia = map.get(id);
            if (ia != null) {
                ia.setOrdemEmbarque(ordem++);
            }
        }

        itinerarioAlunoRepository.saveAll(atuais);
    }

    @Transactional
    public List<AlunoComLocalizacao> buscarAlunosComLocalizacao(Long itinerarioId) {
        Itinerario itinerario = itinerarioRepository.findById(itinerarioId)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        return itinerarioAlunoRepository.findByItinerarioOrderByOrdemEmbarqueAsc(itinerario).stream()
                .filter(ia -> ia.getEndereco() != null && ia.getEndereco().getLatitude() != null && ia.getEndereco().getLongitude() != null)
                .map(ia -> {
                    Endereco endereco = ia.getEndereco();
                    String enderecoCompleto = String.format("%s, %s - %s",
                            endereco.getLogradouro(),
                            endereco.getNumero(),
                            endereco.getBairro()
                    );

                    return new AlunoComLocalizacao(
                            ia.getAluno().getIdAluno(),
                            ia.getAluno().getNome(),
                            endereco.getIdEndereco(),
                            enderecoCompleto,
                            endereco.getLatitude(),
                            endereco.getLongitude(),
                            ia.getOrdemEmbarque()
                    );
                })
                .toList();
    }
}
