package com.safeway.tech.dto;

public record ResponsavelResponse(
        Long id, // novo campo
        String nome,
        String cpf,
        String tel1,
        String tel2,
        String email,
        EnderecoResponse endereco
) {

    public static ResponsavelResponse fromEntity(com.safeway.tech.models.Responsavel responsavel) {
        return new ResponsavelResponse(
                responsavel.getIdResponsavel(),
                responsavel.getNome(),
                responsavel.getCpf(),
                responsavel.getTel1(),
                responsavel.getTel2(),
                responsavel.getEmail(),
                EnderecoResponse.fromEntity(responsavel.getEndereco())
        );
    }
}
