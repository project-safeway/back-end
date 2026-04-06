package com.safeway.tech.api.dto.itinerario;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ItinerarioAlunoRequest(
        @NotNull UUID alunoId,
        @NotNull Integer ordemEmbarque,
        UUID enderecoId
) {
}
