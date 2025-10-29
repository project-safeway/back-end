package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;

import java.sql.Time;
import java.util.List;

public record ItinerarioResponse(
        Long id,
        String nome,
        Time horarioInicio,
        Time horarioFim,
        TipoViagemEnum tipoViagem,
        Boolean ativo,
        List<ItinerarioAlunoResponse> alunos
) {
}
