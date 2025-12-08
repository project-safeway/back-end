package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoRequest(
        @NotNull Long alunoId,
        Long responsavelId,

        @NotBlank @Size(max = 255) String logradouro,
        @NotBlank @Size(max = 10) String numero,
        @Size(max = 100) String complemento,
        @NotBlank @Size(max = 100) String bairro,
        @NotBlank @Size(max = 100) String cidade,
        @NotBlank @Size(min = 2, max = 2) String uf,
        @NotBlank @Size(max = 9) String cep,

        @NotNull Double latitude,
        @NotNull Double longitude,

        @NotBlank @Size(max = 50) String tipo,
        Boolean principal
) {
}
