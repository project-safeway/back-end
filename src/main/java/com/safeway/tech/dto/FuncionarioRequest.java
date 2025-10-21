package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioRequest(
        @NotNull TransporteRequest transporte,
        @NotNull EnderecoRequest endereco,
        @NotBlank String nome,
        @NotBlank String cpf
) {
}
