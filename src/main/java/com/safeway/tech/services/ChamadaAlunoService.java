package com.safeway.tech.services;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.enums.StatusPresencaEnum;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Chamada;
import com.safeway.tech.models.ChamadaAluno;
import com.safeway.tech.repository.ChamadaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
public class ChamadaAlunoService {

    @Autowired
    private ChamadaAlunoRepository chamadaAlunoRepository;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ChamadaService chamadaService;

    @Transactional
    public void registrarPresenca(Map<UUID, StatusPresencaEnum> presencas, UUID idChamada) {
        Chamada chamada = chamadaService.buscarChamadaPorId(idChamada);

        if (!StatusChamadaEnum.EM_ANDAMENTO.equals(chamada.getStatus())) {
            throw new RuntimeException("Chamada não está em andamento");
        }

        for (Map.Entry<UUID, StatusPresencaEnum> entry : presencas.entrySet()) {
            UUID idAluno = entry.getKey();
            StatusPresencaEnum status = entry.getValue();

            Aluno aluno = alunoService.buscarAlunoPorId(idAluno);

            ChamadaAluno chamadaAluno = chamadaAlunoRepository
                    .findByChamadaAndAluno(chamada, aluno)
                    .orElseGet(() -> {
                        ChamadaAluno ca = new ChamadaAluno();
                        ca.setChamada(chamada);
                        ca.setAluno(aluno);
                        return ca;
                    });

            chamadaAluno.setPresenca(status);
            chamadaAluno.setData(LocalDateTime.now());

            chamadaAlunoRepository.save(chamadaAluno);
        }
    }



}
