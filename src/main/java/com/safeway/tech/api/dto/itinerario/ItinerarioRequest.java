package com.safeway.tech.api.dto.itinerario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safeway.tech.domain.enums.TipoViagemEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItinerarioRequest(
        @NotBlank String nome,
        Time horarioInicio,
        Time horarioFim,
        @NotNull TipoViagemEnum tipoViagem
) {
}
