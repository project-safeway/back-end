package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.itinerario.ItinerarioRequest;
import com.safeway.tech.api.dto.itinerario.ItinerarioResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioUpdateRequest;
import com.safeway.tech.api.dto.itinerario.ItinerarioUpdateRequest.ItinerarioParadaUpdate;
import com.safeway.tech.domain.models.Itinerario;
import com.safeway.tech.domain.models.ItinerarioAluno;
import com.safeway.tech.domain.models.ItinerarioEscola;
import com.safeway.tech.repository.ItinerarioAlunoRepository;
import com.safeway.tech.repository.ItinerarioEscolaRepository;
import com.safeway.tech.repository.ItinerarioRepository;
import com.safeway.tech.service.mappers.ItinerarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItinerarioService {

    private final ItinerarioRepository itinerarioRepository;
    private final TransporteService transporteService;
    private final ItinerarioAlunoService itinerarioAlunoService;
    private final ItinerarioAlunoRepository itinerarioAlunoRepository;
    private final ItinerarioEscolaRepository itinerarioEscolaRepository;

    public List<ItinerarioResponse> listarTodos(UUID transporteId) {
        return itinerarioRepository.findAllByTransporte(transporteId).stream()
                .map(ItinerarioMapper::toResponse)
                .filter(ItinerarioResponse::ativo)
                .toList();
    }

    public Itinerario buscarPorId(UUID id) {
        return itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
    }

    @Transactional
    public void desativar(UUID id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
        itinerario.setAtivo(false);
        itinerarioRepository.save(itinerario);
    }

    @Transactional
    public ItinerarioResponse criar(ItinerarioRequest request, UUID transporteUsuario) {
        if (!request.transporteId().equals(transporteUsuario)) {
            throw new RuntimeException("Sem permissão para acessar este transporte");
        }

        Itinerario itinerario = new Itinerario();
        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());
        itinerario.setTransporte(transporteService.buscarPorId(request.transporteId()));
        itinerarioRepository.save(itinerario);
        return ItinerarioMapper.toResponse(itinerario);
    }

    @Transactional
    public ItinerarioResponse atualizar(UUID id, ItinerarioUpdateRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());
        itinerario.setAtivo(request.ativo());

        // Sincroniza alunos (criar/remover) se lista for enviada
        if (request.alunos() != null && !request.alunos().isEmpty()) {
            itinerarioAlunoService.sincronizarAlunos(itinerario, request.alunos());
        }

        // Aplica ordemGlobal e ordemEspecifica na lista unificada de paradas, se enviada
        if (request.paradas() != null && !request.paradas().isEmpty()) {
            // Carrega vínculos atuais
            var alunosAtuais = itinerarioAlunoRepository.findByItinerarioId(itinerario.getId());
            var escolasAtuais = itinerarioEscolaRepository.findByItinerarioId(itinerario.getId());

            java.util.Map<UUID, ItinerarioAluno> alunosPorId = alunosAtuais.stream()
                    .collect(java.util.stream.Collectors.toMap(a -> a.getAluno().getId(), a -> a));

            java.util.Map<UUID, ItinerarioEscola> escolasPorId = escolasAtuais.stream()
                    .collect(java.util.stream.Collectors.toMap(e -> e.getEscola().getId(), e -> e));

            for (ItinerarioParadaUpdate parada : request.paradas()) {
                if (parada == null || parada.id() == null) {
                    continue;
                }
                if ("ALUNO".equalsIgnoreCase(parada.tipo())) {
                    ItinerarioAluno ia = alunosPorId.get(parada.id());
                    if (ia != null) {
                        ia.setOrdemGlobal(parada.ordemGlobal());
                        ia.setOrdemEmbarque(parada.ordemEspecifica());
                    }
                } else if ("ESCOLA".equalsIgnoreCase(parada.tipo())) {
                    ItinerarioEscola ie = escolasPorId.get(parada.id());
                    if (ie != null) {
                        ie.setOrdemGlobal(parada.ordemGlobal());
                        ie.setOrdemParada(parada.ordemEspecifica());
                    }
                }
            }

            itinerarioAlunoRepository.saveAll(alunosAtuais);
            itinerarioEscolaRepository.saveAll(escolasAtuais);
        }

        itinerarioRepository.save(itinerario);
        return ItinerarioMapper.toResponse(itinerario);
    }

}
