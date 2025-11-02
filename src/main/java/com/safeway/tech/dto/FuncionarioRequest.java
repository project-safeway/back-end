package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FuncionarioRequest(
        @NotNull TransporteRequest transporte,
        @NotNull EnderecoRequest endereco,
        @NotBlank @Size(max = 100) String nome,
        @NotBlank @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter 11 dígitos numéricos") String cpf
) {
}
