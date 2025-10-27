package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;

import java.time.LocalTime;

public record ItinerarioRequest(
        Long idTransporte,
        String nome,
        LocalTime horarioInicio,
        LocalTime horarioFim,
        TipoViagemEnum tipoViagem
) {
}
