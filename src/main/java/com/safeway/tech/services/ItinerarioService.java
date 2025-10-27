package com.safeway.tech.services;

import com.safeway.tech.dto.AlunoItinerarioRequest;
import com.safeway.tech.dto.ItinerarioRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.ItinerarioAlunoRepository;
import com.safeway.tech.repository.ItinerarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItinerarioService {

    @Autowired
    private ItinerarioRepository itinerarioRepository;

    @Autowired
    private ItinerarioAlunoRepository itinerarioAlunoRepository; // Precisa mudar pra chamar o service

    @Autowired
    private TransporteService transporteService;

    @Autowired
    private AlunoService alunoService;

    public Itinerario cadastrarItinerario(ItinerarioRequest request) {
        Itinerario itinerario = new Itinerario();
        itinerario.setNome(request.nome());
        itinerario.setHorarioInicio(request.horarioInicio());
        itinerario.setHorarioFim(request.horarioFim());
        itinerario.setTipoViagem(request.tipoViagem());

        Transporte transporte = transporteService.getById(request.idTransporte());
        itinerario.setTransporte(transporte);

        return itinerarioRepository.save(itinerario);
    }

    public Itinerario cadastrarAlunoItinerario(AlunoItinerarioRequest request) {
        Itinerario itinerario = itinerarioRepository.findById(request.idItinerario())
                .orElseThrow(() -> new RuntimeException("Itinerário não encontrado com o ID: " + request.idItinerario()));

        Aluno aluno = alunoService.buscarAlunoPorId(request.idAluno());

        Boolean alunoJaExiste = itinerario.getAlunos().stream()
                .anyMatch(ia -> ia.getAluno().equals(aluno));

        if (alunoJaExiste) {
            throw new RuntimeException("Aluno já está cadastrado neste itinerário.");
        }

        ItinerarioAluno itinerarioAluno = new ItinerarioAluno();
        itinerarioAluno.setItinerario(itinerario);
        itinerarioAluno.setAluno(aluno);
        itinerarioAluno.setOrdemEmbarque(itinerario.getAlunos().size()); // Ainda precisa definir uma forma melhor de buscar a ordem
        itinerarioAluno = itinerarioAlunoRepository.save(itinerarioAluno);
        itinerario.getAlunos().add(itinerarioAluno);

        return itinerarioRepository.save(itinerario);
    }

    public List<Itinerario> listarItinerarios(Transporte transporte) {
        return itinerarioRepository.findByTransporte(transporte);
    }

    public void deletarItinerario(Long id) {
        itinerarioRepository.deleteById(id);
    }

}
