package com.safeway.tech.dto;

public record ItinerarioAlunoResponse(
        Long alunoId,
        String nomeAluno,
        Integer ordemEmbarque,
        Long enderecoId
) {

    public static ItinerarioAlunoResponse fromEntity(com.safeway.tech.models.ItinerarioAluno itinerarioAluno) {
        return new ItinerarioAlunoResponse(
                itinerarioAluno.getAluno().getIdAluno(),
                itinerarioAluno.getAluno().getNome(),
                itinerarioAluno.getOrdemEmbarque(),
                itinerarioAluno.getEndereco() != null ? itinerarioAluno.getEndereco().getIdEndereco() : null
        );
    }
}
