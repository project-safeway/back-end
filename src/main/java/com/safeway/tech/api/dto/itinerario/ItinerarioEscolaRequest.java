package com.safeway.tech.api.dto.itinerario;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItinerarioEscolaRequest(
        @NotNull UUID escolaId,
        UUID enderecoId,
        Integer ordemParada
) {
}

