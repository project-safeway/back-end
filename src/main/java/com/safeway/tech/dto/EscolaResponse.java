package com.safeway.tech.dto;

import com.safeway.tech.enums.NivelEnsinoEnum;
import com.safeway.tech.models.Escola;

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
