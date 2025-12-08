package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusPresencaEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ChamadaAlunoResponse(
        AlunoResponse aluno,
        StatusPresencaEnum presenca,
        LocalDateTime dataHora
) {

    public static ChamadaAlunoResponse fromEntity(com.safeway.tech.models.ChamadaAluno chamadaAluno) {
        return new ChamadaAlunoResponse(
                AlunoResponse.fromEntity(chamadaAluno.getAluno()),
                chamadaAluno.getPresenca(),
                chamadaAluno.getData()
        );
    }

    public record AlunoResponse(
            String nome,
            String professor,
            LocalDate dtNascimento,
            Integer serie,
            String sala,
            EscolaResponse escola
    ) {

        public static AlunoResponse fromEntity(com.safeway.tech.models.Aluno aluno) {
            return new AlunoResponse(
                    aluno.getNome(),
                    aluno.getProfessor(),
                    aluno.getDtNascimento(),
                    aluno.getSerie(),
                    aluno.getSala(),
                    EscolaResponse.fromEntity(aluno.getEscola())
            );
        }
    }
}
