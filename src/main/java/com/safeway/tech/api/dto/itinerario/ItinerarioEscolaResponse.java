package com.safeway.tech.api.dto.itinerario;

import java.util.UUID;

public record ItinerarioEscolaResponse(
        UUID escolaId,
        String nome,
        String cidade,
        Integer ordemVisita,
        UUID enderecoId,
        Integer ordemGlobal
) {
}
