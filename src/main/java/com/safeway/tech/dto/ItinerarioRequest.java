package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;

import java.sql.Time;

public record ItinerarioRequest(
        String nome,
        Long transporteId,
        Time horarioInicio,
        Time horarioFim,
        TipoViagemEnum tipoViagem
) {
}
