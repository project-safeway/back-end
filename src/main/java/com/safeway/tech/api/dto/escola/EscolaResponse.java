package com.safeway.tech.api.dto.escola;

import com.safeway.tech.api.dto.aluno.AlunoResumeResponse;
import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.enums.NivelEnsinoEnum;

import java.util.List;

public record EscolaResponse(
        String nome,
        NivelEnsinoEnum nivelEnsino,
        EnderecoResponse endereco,
        List<AlunoResumeResponse> alunos
) {
}
