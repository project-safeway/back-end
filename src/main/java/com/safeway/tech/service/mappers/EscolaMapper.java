package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.escola.EscolaResponse;
import com.safeway.tech.api.dto.escola.EscolaResumeResponse;
import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.Escola;

import java.util.Collections;
import java.util.List;

public class EscolaMapper {

    public static EscolaResponse toResponse(Escola escola) {
        List<Aluno> alunos = escola.getAlunos() != null ? escola.getAlunos() : Collections.emptyList();

        return new EscolaResponse(
                escola.getId(),
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoMapper.toResponse(escola.getEndereco()),
                alunos.stream().map(AlunoMapper::toResumeResponse).toList()
        );
    }

    public static EscolaResumeResponse toResumeResponse(Escola escola) {
        return new EscolaResumeResponse(
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoMapper.toResponse(escola.getEndereco())
        );
    }

}
