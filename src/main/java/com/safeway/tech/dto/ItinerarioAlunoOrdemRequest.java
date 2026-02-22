package com.safeway.tech.dto;

import java.util.List;
import java.util.UUID;

public record ItinerarioAlunoOrdemRequest(
    List<UUID> alunosId
) {
}
