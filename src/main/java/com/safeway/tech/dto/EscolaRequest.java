package com.safeway.tech.dto;

import com.safeway.tech.enums.NivelEnsinoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EscolaRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotNull NivelEnsinoEnum nivelEnsino,
        @NotNull @Valid EnderecoData endereco
) {
    public record EnderecoData(
            @NotBlank @Size(max = 255) String logradouro,
            @NotBlank @Size(max = 10) String numero,
            @Size(max = 100) String complemento,
            @NotBlank @Size(max = 100) String bairro,
            @NotBlank @Size(max = 100) String cidade,
            @NotBlank @Size(min = 2, max = 2) String uf,
            @NotBlank @Size(max = 9) String cep,
            Double latitude,
            Double longitude
    ) {}
}