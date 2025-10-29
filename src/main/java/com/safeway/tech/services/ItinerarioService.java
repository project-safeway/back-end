package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.dto.ItinerarioUpdateRequest;
import com.safeway.tech.mappers.ItinerarioMapper;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Transporte;
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

    public List<ItinerarioResponse> listarTodos() {
        return itinerarioRepository.findAll().stream()
                .map(ItinerarioMapper::toResponse)
                .toList();
    }

    public ItinerarioResponse buscarPorId(Long id) {
        Itinerario entity = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
        return ItinerarioMapper.toResponse(entity);
    }

    @Transactional
    public void desativar(Long id) {
        Itinerario itinerario = itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
        itinerario.setAtivo(false);
        itinerarioRepository.save(itinerario);
    }

    @Transactional
    public ItinerarioResponse criar(ItinerarioRequest request) {
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

        if (request.alunos() != null) {
            itinerarioAlunoService.sincronizarAlunos(itinerario, request.alunos());
        }

        itinerarioRepository.save(itinerario);
        return ItinerarioMapper.toResponse(itinerario);
    }

}
