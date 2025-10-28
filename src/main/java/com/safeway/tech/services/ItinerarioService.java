package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private TransporteService transporteService;

    public Itinerario buscarItinerarioPorId(Long id) {
        return itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
    }

    public List<Itinerario> buscarTodosItinerarios(Long idTransporte) {
        return itinerarioRepository.findAllByTransporteId(idTransporte);
    }

    public Itinerario criarItinerario(ItinerarioRequest request) {
        Itinerario itinerario = new Itinerario();

        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());

        Transporte transporte = transporteService.getById(request.transporteId());

        itinerario.setTransporte(transporte);
        return itinerarioRepository.save(itinerario);
    }

    public Itinerario atualizarItinerario(Long id, ItinerarioRequest request) {
        Itinerario itinerario = buscarItinerarioPorId(id);

        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());

        Transporte transporte = transporteService.getById(request.transporteId());
        itinerario.setTransporte(transporte);

        return itinerarioRepository.save(itinerario);
    }

    public void deleterItinerario(Long id) {
        Itinerario itinerario = buscarItinerarioPorId(id);
        itinerarioRepository.delete(itinerario);
    }

}
