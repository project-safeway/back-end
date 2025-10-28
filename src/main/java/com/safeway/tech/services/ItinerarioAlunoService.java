package com.safeway.tech.services;

import com.safeway.tech.dto.ItinerarioAlunoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.repository.ItinerarioAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ItinerarioAlunoService {

    @Autowired
    private ItinerarioAlunoRepository itinerarioAlunoRepository;

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private AlunoService alunoService;

    public ItinerarioAluno buscarPorId(Long id){
        return itinerarioAlunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerário do Aluno não encontrado"));
    }

    public ItinerarioAluno cadastrarAluno(ItinerarioAlunoRequest request) {
        Itinerario itinerario = itinerarioService.buscarItinerarioPorId(request.itinerarioId());
        Aluno aluno = alunoService.buscarAlunoPorId(request.alunoId());

        List<ItinerarioAluno> atuais = itinerarioAlunoRepository.findByItinerario(itinerario.getId());
        for (ItinerarioAluno ia : atuais) {
            if (ia.getAluno().getIdAluno().equals(aluno.getIdAluno())) {
                throw new RuntimeException("Aluno já cadastrado no itinerário");
            }
        }

        ItinerarioAluno itinerarioAluno = new ItinerarioAluno();
        itinerarioAluno.setItinerario(itinerario);
        itinerarioAluno.setAluno(aluno);
        int nextOrdem = atuais.getLast().getOrdemEmbarque() + 1;
        itinerarioAluno.setOrdemEmbarque(nextOrdem);

        return itinerarioAlunoRepository.save(itinerarioAluno);
    }

    public void removerAluno(Long itinerarioAlunoId) {
        ItinerarioAluno itinerarioAluno = buscarPorId(itinerarioAlunoId);
        itinerarioAlunoRepository.delete(itinerarioAluno);
    }

    @Transactional
    public void reordenarAlunos(Long itinerarioId, List<Long> alunoIds) {
        List<ItinerarioAluno> atuais = itinerarioAlunoRepository.findByItinerario(itinerarioId);

        Map<Long, ItinerarioAluno> map = new LinkedHashMap<>();
        for (ItinerarioAluno ia : atuais) {
            map.put(ia.getAluno().getIdAluno(), ia);
        }

        List<ItinerarioAluno> novaOrdem = new ArrayList<>();
        int ordem = 1;
        Set<Long> processado = new HashSet<>();

        if (alunoIds != null) {
            for (Long alunoId : alunoIds) {
                ItinerarioAluno ia = map.get(alunoId);
                if (ia != null) {
                    ia.setOrdemEmbarque(ordem++);
                    novaOrdem.add(ia);
                    processado.add(alunoId);
                }
            }
        }

        for (ItinerarioAluno ia : atuais) {
            Long idAluno = ia.getAluno().getIdAluno();
            if (!processado.contains(idAluno)) {
                ia.setOrdemEmbarque(ordem++);
                novaOrdem.add(ia);
            }
        }

        if (!novaOrdem.isEmpty()) {
            itinerarioAlunoRepository.saveAll(novaOrdem);
        }
    }

}
