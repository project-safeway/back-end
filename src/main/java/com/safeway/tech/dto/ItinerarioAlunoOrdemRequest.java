package com.safeway.tech.dto;

import java.util.List;

public record ItinerarioAlunoOrdemRequest(
    List<Long> alunosId
) {
}
