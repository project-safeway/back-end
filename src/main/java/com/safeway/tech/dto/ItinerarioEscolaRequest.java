package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;

public record ItinerarioEscolaRequest(
        @NotNull Long escolaId,
        Long enderecoId,
        Integer ordemParada
) {
}

