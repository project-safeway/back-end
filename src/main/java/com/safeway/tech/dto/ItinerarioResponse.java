package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;
import com.safeway.tech.models.ItinerarioEscola;

import java.sql.Time;
import java.util.Comparator;
import java.util.List;

public record ItinerarioResponse(
        Long id,
        String nome,
        Time horarioInicio,
        Time horarioFim,
        TipoViagemEnum tipoViagem,
        Boolean ativo,
        List<ItinerarioAlunoResponse> alunos,
        List<ItinerarioEscolaResponse> escolas
) {

    public static ItinerarioResponse fromEntity(com.safeway.tech.models.Itinerario itinerario) {
        List<ItinerarioAlunoResponse> alunosResponse = itinerario.getAlunos().stream()
                .sorted(Comparator.comparing(a -> a.getOrdemEmbarque() == null ? Integer.MAX_VALUE : a.getOrdemEmbarque()))
                .map(ItinerarioAlunoResponse::fromEntity)
                .toList();

        List<ItinerarioEscolaResponse> escolasResponse = itinerario.getEscolas().stream()
                .sorted(Comparator.comparing(e -> e.getOrdemParada() == null ? Integer.MAX_VALUE : e.getOrdemParada()))
                .map(ItinerarioEscolaResponse::fromEntity)
                .toList();

        return new ItinerarioResponse(
                itinerario.getId(),
                itinerario.getNome(),
                itinerario.getHorarioInicio(),
                itinerario.getHorarioFim(),
                itinerario.getTipoViagem(),
                itinerario.getAtivo(),
                alunosResponse,
                escolasResponse
        );
    }
}
