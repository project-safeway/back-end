package com.safeway.tech.api.dto.itinerario;

import com.safeway.tech.domain.enums.TipoViagemEnum;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

public record ItinerarioResponse(
        UUID id,
        String nome,
        Time horarioInicio,
        Time horarioFim,
        TipoViagemEnum tipoViagem,
        Boolean ativo,
        List<ItinerarioAlunoResponse> alunos,
        List<ItinerarioEscolaResponse> escolas
) {
}
