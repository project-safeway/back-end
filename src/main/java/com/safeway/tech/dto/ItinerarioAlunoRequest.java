package com.safeway.tech.dto;

public record ItinerarioAlunoRequest(
    Long itinerarioId,
    Long alunoId,
    Integer ordemEmbarque
) {
}
