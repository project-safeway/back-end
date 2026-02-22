package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusChamadaEnum;
import com.safeway.tech.models.Chamada;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ChamadaResponse(
        UUID id,
        ItinerarioResponse itinerario,
        StatusChamadaEnum status,
        List<ChamadaAlunoResponse> alunos
) {

    public static ChamadaResponse fromEntity(Chamada chamada) {
        List<ChamadaAlunoResponse> alunosResponse = new ArrayList<>();
        if (chamada.getAlunos() != null && !chamada.getAlunos().isEmpty()) {
            alunosResponse = chamada.getAlunos().stream()
                    .map(ChamadaAlunoResponse::fromEntity)
                    .toList();
        }

        return new ChamadaResponse(
            chamada.getId(),
            ItinerarioResponse.fromEntity(chamada.getItinerario()),
            chamada.getStatus(),
            alunosResponse
        );
    }
}
