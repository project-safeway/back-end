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

    public static ItinerarioResponse fromEntity(com.safeway.tech.models.Itinerario itinerario) {
        List<ItinerarioAlunoResponse> alunosResponse = itinerario.getAlunos().stream()
                .map(ItinerarioAlunoResponse::fromEntity)
                .toList();

        return new ItinerarioResponse(
                itinerario.getId(),
                itinerario.getNome(),
                itinerario.getHorarioInicio(),
                itinerario.getHorarioFim(),
                itinerario.getTipoViagem(),
                itinerario.getAtivo(),
                alunosResponse
        );
    }
}
