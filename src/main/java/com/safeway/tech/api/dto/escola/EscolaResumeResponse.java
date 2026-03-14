package com.safeway.tech.api.dto.escola;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.enums.NivelEnsinoEnum;

public record EscolaResumeResponse(
        String nome,
        NivelEnsinoEnum nivelEnsino,
        EnderecoResponse endereco
) {
}
