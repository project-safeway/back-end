package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.models.Aluno;
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

    /**
     * Adiciona um aluno a um itinerário existente
     */
    @Transactional
    public void adicionarAluno(Long itinerarioId, ItinerarioAlunoRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(itinerarioId)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        Aluno aluno = alunoService.buscarAlunoPorId(request.alunoId());

        // Evita duplicidade
        itinerarioAlunoRepository.findByItinerarioIdAndAlunoId(itinerarioId, aluno.getIdAluno())
                .ifPresent(a -> {
                    throw new RuntimeException("Aluno já está vinculado a este itinerário");
                });

        ItinerarioAluno entity = new ItinerarioAluno();
        entity.setItinerario(itinerario);
        entity.setAluno(aluno);
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

        // Cria novos vínculos
        List<ItinerarioAluno> entidades = novos.stream().map(dto -> {
            ItinerarioAluno ia = new ItinerarioAluno();
            ia.setItinerario(itinerario);
            ia.setAluno(alunoService.buscarAlunoPorId(dto.alunoId()));
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

}
