package com.safeway.tech.dto;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Transporte;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record FuncionarioResponse(
        UUID idFuncionario,
        String nome,
        String cpf,
        TransporteResponse transporte,
        EnderecoResponse endereco
) {

    public static FuncionarioResponse fromEntity(Funcionario funcionario) {
        return new FuncionarioResponse(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                TransporteResponse.fromEntity(funcionario.getTransporte()),
                EnderecoResponse.fromEntity(funcionario.getEndereco())
        );
    }

    @Schema(name = "TransporteFuncionarioResponse")
    public record TransporteResponse(
            String placa,
            String modelo,
            Integer capacidade
    ) {
        public static TransporteResponse fromEntity(Transporte transporte) {
            return new TransporteResponse(
                    transporte.getPlaca(),
                    transporte.getModelo(),
                    transporte.getCapacidade()
            );
        }
    }
}
