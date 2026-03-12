package com.safeway.tech.api.dto.escola;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.enums.NivelEnsinoEnum;
import com.safeway.tech.domain.models.Escola;

public record EscolaResponse(
        String nome,
        NivelEnsinoEnum nivelEnsino,
        EnderecoResponse endereco
) {

    public static EscolaResponse fromEntity(Escola escola) {
        return new EscolaResponse(
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoResponse.fromEntity(escola.getEndereco())
        );
    }
}
