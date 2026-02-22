package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItinerarioAlunoRequest(
        @NotNull UUID alunoId,
        @NotNull Integer ordemEmbarque,
        UUID enderecoId
) {
}
