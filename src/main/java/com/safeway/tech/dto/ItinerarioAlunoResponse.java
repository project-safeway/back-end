package com.safeway.tech.dto;

import com.safeway.tech.models.ItinerarioAluno;
import com.safeway.tech.models.Responsavel;

public record ItinerarioAlunoResponse(
        Long alunoId,
        String nomeAluno,
        Integer ordemEmbarque,
        Long enderecoId,
        Integer ordemGlobal,
        String nomeEscola,
        String nomeResponsavel,
        String sala
) {

    public static ItinerarioAlunoResponse fromEntity(ItinerarioAluno itinerarioAluno) {
        return new ItinerarioAlunoResponse(
                itinerarioAluno.getAluno().getIdAluno(),
                itinerarioAluno.getAluno().getNome(),
                itinerarioAluno.getOrdemEmbarque(),
                itinerarioAluno.getEndereco() != null ? itinerarioAluno.getEndereco().getIdEndereco() : null,
                itinerarioAluno.getOrdemGlobal(),
                itinerarioAluno.getAluno().getEscola().getNome(),
                itinerarioAluno.getAluno().getResponsaveis().stream().map(Responsavel::getNome).findFirst().orElse(null),
                itinerarioAluno.getAluno().getSala()
        );
    }
}
