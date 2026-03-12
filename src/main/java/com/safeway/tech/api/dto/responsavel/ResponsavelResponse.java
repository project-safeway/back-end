package com.safeway.tech.api.dto.responsavel;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.models.Responsavel;

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

    public static ResponsavelResponse fromEntity(Responsavel responsavel) {
        return new ResponsavelResponse(
                responsavel.getId(),
                responsavel.getNome(),
                responsavel.getCpf(),
                responsavel.getTel1(),
                responsavel.getTel2(),
                responsavel.getEmail(),
                EnderecoResponse.fromEntity(responsavel.getEndereco())
        );
    }
}
