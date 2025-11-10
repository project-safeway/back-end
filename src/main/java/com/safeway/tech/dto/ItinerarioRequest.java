package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;

public record ItinerarioRequest(
        @NotBlank String nome,
        @NotNull Long transporteId,
        Time horarioInicio,
        Time horarioFim,
        @NotNull TipoViagemEnum tipoViagem
) {
}
