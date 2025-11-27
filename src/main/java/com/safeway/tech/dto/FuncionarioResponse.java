package com.safeway.tech.dto;

public record FuncionarioResponse(
        Long idFuncionario,
        String nome,
        String cpf,
        TransporteResponse transporte,
        EnderecoResponse endereco
) {

    public static FuncionarioResponse fromEntity(com.safeway.tech.models.Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getIdFuncionario(),
                funcionario.getNome(),
                funcionario.getCpf(),
                TransporteResponse.fromEntity(funcionario.getTransporte()),
                EnderecoResponse.fromEntity(funcionario.getEndereco())
        );
    }
}
