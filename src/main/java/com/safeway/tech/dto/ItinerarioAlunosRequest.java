package com.safeway.tech.dto;

import java.util.List;

public record ItinerarioAlunosRequest(
        List<Integer> idAlunosAdicao,
        List<Integer> idAlunosRemocao
) {
}
