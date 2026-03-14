package com.safeway.tech.api.dto.escola;

import com.safeway.tech.api.dto.endereco.EnderecoRequest;
import com.safeway.tech.domain.enums.NivelEnsinoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EscolaRequest(
        @NotBlank @Size(max = 100) String nome,
        @NotNull NivelEnsinoEnum nivelEnsino,
        @NotNull @Valid EnderecoRequest endereco
) {
}
