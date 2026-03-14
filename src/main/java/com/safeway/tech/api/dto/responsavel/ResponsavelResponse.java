package com.safeway.tech.api.dto.responsavel;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;

import java.util.UUID;

public record ResponsavelResponse(
        UUID id, // novo campo
        String nome,
        String cpf,
        String tel1,
        String tel2,
        String email,
        EnderecoResponse endereco
) {
}
