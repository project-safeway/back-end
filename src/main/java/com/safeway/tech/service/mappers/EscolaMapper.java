package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.escola.EscolaResponse;
import com.safeway.tech.api.dto.escola.EscolaResumeResponse;
import com.safeway.tech.domain.models.Escola;

public class EscolaMapper {

    public static EscolaResponse toResponse(Escola escola) {
        return new EscolaResponse(
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoMapper.toResponse(escola.getEndereco()),
                escola.getAlunos().stream().map(AlunoMapper::toResumeResponse).toList()
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
