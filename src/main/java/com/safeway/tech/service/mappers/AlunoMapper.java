package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.aluno.AlunoResponse;
import com.safeway.tech.api.dto.aluno.AlunoResumeResponse;
import com.safeway.tech.domain.models.Aluno;

public class AlunoMapper {

    public static AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getNome(),
                aluno.getProfessor(),
                aluno.getDtNascimento(),
                aluno.getSerie(),
                aluno.getSala(),
                EscolaMapper.toResumeResponse(aluno.getEscola()),
                aluno.getResponsaveis().stream().map(ResponsavelMapper::toResponse).toList(),
                aluno.getValorMensalidade(),
                aluno.getDiaVencimento()
        );
    }

    public static AlunoResumeResponse toResumeResponse(Aluno aluno) {
        return new AlunoResumeResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getSerie(),
                aluno.getSala(),
                aluno.getAtivo()
        );
    }

}
