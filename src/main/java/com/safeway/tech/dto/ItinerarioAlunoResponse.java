package com.safeway.tech.dto;

import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.models.Responsavel;

import java.util.UUID;

public record ItinerarioAlunoResponse(
        UUID alunoId,
        String nomeAluno,
        Integer ordemEmbarque,
        UUID enderecoId,
        Integer ordemGlobal,
        String nomeEscola,
        String nomeResponsavel,
        String sala
) {

    public static ItinerarioAlunoResponse fromEntity(ItinerarioAluno itinerarioAluno) {
        return new ItinerarioAlunoResponse(
                itinerarioAluno.getAluno().getId(),
                itinerarioAluno.getAluno().getNome(),
                itinerarioAluno.getOrdemEmbarque(),
                itinerarioAluno.getEndereco() != null ? itinerarioAluno.getEndereco().getId() : null,
                itinerarioAluno.getOrdemGlobal(),
                itinerarioAluno.getAluno().getEscola().getNome(),
                itinerarioAluno.getAluno().getResponsaveis().stream().map(Responsavel::getNome).findFirst().orElse(null),
                itinerarioAluno.getAluno().getSala()
        );
    }
}
