package com.safeway.tech.services;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.repository.ChamadaRepository;
import com.safeway.tech.specification.ChamadaSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChamadaService {

    @Autowired
    private ChamadaRepository chamadaRepository;

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private CurrentUserService currentUserService;

    public Chamada buscarChamadaPorId(Long id) {
        return chamadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));
    }

    public Chamada buscarChamadaAtivaPorItinerario(Long idItinerario) {
        return chamadaRepository.findByItinerarioIdAndStatus(idItinerario, StatusChamadaEnum.EM_ANDAMENTO)
                .orElse(null);
    }

    public Chamada iniciarChamada(Long idItinerario) {
        Chamada chamadaExistente = buscarChamadaAtivaPorItinerario(idItinerario);
        if(chamadaExistente != null) {
            throw new RuntimeException("Já existe uma chamada em andamento para este itinerário");
        }

        Itinerario itinerario = itinerarioService.buscarPorId(idItinerario);

        Chamada chamada = new Chamada();
        chamada.setItinerario(itinerario);
        chamada.setStatus(StatusChamadaEnum.EM_ANDAMENTO);

        chamadaRepository.save(chamada);

        return chamada;
    }

    public Chamada atualizarChamada(Long idItinerario, StatusChamadaEnum statusChamada) {
        Chamada chamada = buscarChamadaAtivaPorItinerario(idItinerario);
        if (chamada == null) {
            throw new RuntimeException("Nenhuma chamada em andamento encontrada para este itinerário");
        }

        chamada.setStatus(statusChamada);

        chamadaRepository.save(chamada);

        return chamada;
    }

    public Page<Chamada> buscarHistoricoChamadas(Long idItinerario, List<StatusChamadaEnum> status, Pageable pageable) {
        Long transporteId = currentUserService.getCurrentTransporteId();
        Long userId = currentUserService.getCurrentUserId();

        Specification<Chamada> specs = Specification.allOf(
                ChamadaSpecs.comItinerarioId(idItinerario),
                ChamadaSpecs.comStatus(status),
                ChamadaSpecs.comTransporte(transporteId),
                ChamadaSpecs.comUsuario(userId)
        );

        return chamadaRepository.findAll(specs, pageable);
    }
}
