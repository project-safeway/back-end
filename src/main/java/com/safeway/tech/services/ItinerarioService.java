package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.dto.ItinerarioUpdateRequest;
import com.safeway.tech.dto.ItinerarioUpdateRequest.ItinerarioParadaUpdate;
import com.safeway.tech.mappers.ItinerarioMapper;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.models.ItinerarioEscola;
import com.safeway.tech.repository.ItinerarioAlunoRepository;
import com.safeway.tech.repository.ItinerarioEscolaRepository;
import com.safeway.tech.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private ItinerarioAlunoService itinerarioAlunoService;

    @Autowired
    private ItinerarioEscolaService itinerarioEscolaService;

    @Autowired
    private ItinerarioAlunoRepository itinerarioAlunoRepository;

    @Autowired
    private ItinerarioEscolaRepository itinerarioEscolaRepository;

    public List<ItinerarioResponse> listarTodos(Long transporteId) {
        return itinerarioRepository.findAllByTransporte(transporteId).stream()
                .map(ItinerarioMapper::toResponse)
                .filter(ItinerarioResponse::ativo)
                .toList();
    }

    public Itinerario buscarPorId(Long id) {
        Itinerario entity = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
        return entity;
    }

    @Transactional
    public void desativar(Long id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
        itinerario.setAtivo(false);
        itinerarioRepository.save(itinerario);
    }

    @Transactional
    public ItinerarioResponse criar(ItinerarioRequest request, Long transporteUsuario) {
        if (!request.transporteId().equals(transporteUsuario)) {
            throw new RuntimeException("Sem permissão para acessar este transporte");
        }

        Itinerario itinerario = new Itinerario();
        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());
        itinerario.setTransporte(transporteService.getById(request.transporteId()));
        itinerarioRepository.save(itinerario);
        return ItinerarioMapper.toResponse(itinerario);
    }

    @Transactional
    public ItinerarioResponse atualizar(Long id, ItinerarioUpdateRequest request) {
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

            java.util.Map<Long, ItinerarioAluno> alunosPorId = alunosAtuais.stream()
                    .collect(java.util.stream.Collectors.toMap(a -> a.getAluno().getIdAluno(), a -> a));

            java.util.Map<Long, ItinerarioEscola> escolasPorId = escolasAtuais.stream()
                    .collect(java.util.stream.Collectors.toMap(e -> e.getEscola().getIdEscola(), e -> e));

            for (ItinerarioParadaUpdate parada : request.paradas()) {
                if (parada == null || parada.id() == null) continue;
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
