package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;

public record ItinerarioAlunoRequest(
        @NotNull Long alunoId,
        @NotNull Integer ordemEmbarque,
        @NotNull Long enderecoId
) {
}
