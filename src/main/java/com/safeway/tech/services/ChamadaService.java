package com.safeway.tech.services;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.repository.ChamadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamadaService {

    @Autowired
    private ChamadaRepository chamadaRepository;

    private ItinerarioService itinerarioService;

    public Chamada buscarChamadaPorId(Long id) {
        return chamadaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));
    }

    public Chamada buscarChamadaAtivaPorItinerario(Long idItinerario) {
        return chamadaRepository.findByItinerarioIdAndStatus(idItinerario, StatusChamadaEnum.EM_ANDAMENTO)
                .orElse(null);
    }

    public void iniciarChamada(Long idItinerario) {
        Chamada chamadaExistente = buscarChamadaAtivaPorItinerario(idItinerario);
        if(chamadaExistente != null) {
            throw new RuntimeException("Já existe uma chamada em andamento para este itinerário");
        }

        Itinerario itinerario = itinerarioService.buscarItinerarioPorId(idItinerario);

        Chamada chamada = new Chamada();
        chamada.setItinerario(itinerario);
        chamada.setStatus(StatusChamadaEnum.EM_ANDAMENTO);

        chamadaRepository.save(chamada);
    }

    public void atualizarChamada(Long idChamada, StatusChamadaEnum statusChamada) {
        Chamada chamada = buscarChamadaPorId(idChamada);
        chamada.setStatus(statusChamada);

        chamadaRepository.save(chamada);
    }
}
