package com.safeway.tech.api.dto.itinerario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItinerarioAlunoRequest(
        @NotNull UUID alunoId,
        @NotNull Integer ordemEmbarque,
        UUID enderecoId
) {
}
