package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private AlunoService alunoService;

    public Itinerario buscarItinerarioPorId(Long id) {
        return itinerarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));
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

    public Itinerario cadastrarAluno(ItinerarioAlunoRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(request.itinerarioId())
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado"));

        Aluno aluno = alunoService.buscarAlunoPorId(request.alunoId());



        return itinerarioRepository.save(itinerario);
    }

}
