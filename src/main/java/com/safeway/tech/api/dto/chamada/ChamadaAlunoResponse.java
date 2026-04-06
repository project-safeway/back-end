package com.safeway.tech.api.dto.chamada;

import com.safeway.tech.api.dto.escola.EscolaResponse;
import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.ChamadaAluno;
import com.safeway.tech.domain.enums.StatusPresencaEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ChamadaAlunoResponse(
        AlunoResponse aluno,
        StatusPresencaEnum presenca,
        LocalDateTime dataHora
) {

    public static ChamadaAlunoResponse fromEntity(ChamadaAluno chamadaAluno) {
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

        public static AlunoResponse fromEntity(Aluno aluno) {
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
