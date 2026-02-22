package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItinerarioEscolaRequest(
        @NotNull UUID escolaId,
        UUID enderecoId,
        Integer ordemParada
) {
}

