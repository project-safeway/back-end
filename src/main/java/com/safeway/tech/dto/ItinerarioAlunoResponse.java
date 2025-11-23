package com.safeway.tech.dto;

import com.safeway.tech.models.ItinerarioAluno;

public record ItinerarioAlunoResponse(
        Long alunoId,
        String nomeAluno,
        Integer ordemEmbarque,
        Long enderecoId,
        Integer ordemGlobal
) {

    public static ItinerarioAlunoResponse fromEntity(ItinerarioAluno itinerarioAluno) {
        return new ItinerarioAlunoResponse(
                itinerarioAluno.getAluno().getIdAluno(),
                itinerarioAluno.getAluno().getNome(),
                itinerarioAluno.getOrdemEmbarque(),
                itinerarioAluno.getEndereco() != null ? itinerarioAluno.getEndereco().getIdEndereco() : null,
                itinerarioAluno.getOrdemGlobal()
        );
    }
}
