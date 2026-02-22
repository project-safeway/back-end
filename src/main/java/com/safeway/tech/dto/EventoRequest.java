package com.safeway.tech.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record EventoRequest(
        UUID id,
        String title,
        String description,
        LocalDate date,
        String type,
        String priority,
        UUID usuarioId,
        String usuarioNome,
        Instant createdAt,
        Instant updatedAt
) {}
