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
import java.util.UUID;

@Service
public class ChamadaService {

    @Autowired
    private ChamadaRepository chamadaRepository;

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private CurrentUserService currentUserService;

    public Chamada buscarChamadaPorId(UUID id) {
        return chamadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));
    }

    public Chamada buscarChamadaAtivaPorItinerario(UUID idItinerario) {
        return chamadaRepository.findByItinerarioIdAndStatus(idItinerario, StatusChamadaEnum.EM_ANDAMENTO)
                .orElse(null);
    }

    public Chamada iniciarChamada(UUID idItinerario) {
        Chamada chamadaExistente = buscarChamadaAtivaPorItinerario(idItinerario);
        if(chamadaExistente != null) {
            return chamadaExistente;
        }

        Itinerario itinerario = itinerarioService.buscarPorId(idItinerario);

        Chamada chamada = new Chamada();
        chamada.setItinerario(itinerario);
        chamada.setStatus(StatusChamadaEnum.EM_ANDAMENTO);

        chamada = chamadaRepository.save(chamada);

        return chamada;
    }

    public Chamada atualizarChamada(UUID idItinerario, StatusChamadaEnum statusChamada) {
        Chamada chamada = buscarChamadaAtivaPorItinerario(idItinerario);
        if (chamada == null) {
            throw new RuntimeException("Nenhuma chamada em andamento encontrada para este itinerário");
        }

        chamada.setStatus(statusChamada);

        chamada = chamadaRepository.save(chamada);

        return chamada;
    }

    public Page<Chamada> buscarHistoricoChamadas(UUID idItinerario, List<StatusChamadaEnum> status, Pageable pageable) {
        UUID transporteId = currentUserService.getCurrentTransporteId();
        UUID userId = currentUserService.getCurrentUserId();

        Specification<Chamada> specs = Specification.allOf(
                ChamadaSpecs.comItinerarioId(idItinerario),
                ChamadaSpecs.comStatus(status),
                ChamadaSpecs.comTransporte(transporteId),
                ChamadaSpecs.comUsuario(userId)
        );

        return chamadaRepository.findAll(specs, pageable);
    }
}
