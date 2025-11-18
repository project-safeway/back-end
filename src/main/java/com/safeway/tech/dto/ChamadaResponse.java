package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;

public record ChamadaResponse(Long id, ItinerarioResponse itinerario, StatusChamadaEnum status) {

    public static ChamadaResponse fromEntity(Chamada chamada) {
        return new ChamadaResponse(
            chamada.getId(),
            ItinerarioResponse.fromEntity(chamada.getItinerario()),
            chamada.getStatus()
        );
    }
}
