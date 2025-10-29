package com.safeway.tech.dto;

public record ItinerarioAlunoResponse(
        Long alunoId,
        String nomeAluno,
        Integer ordemEmbarque
) {
}
