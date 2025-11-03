package com.safeway.tech.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventoRequest(
        Long id,
        String title,
        String description,
        LocalDate date,
        String type,
        String priority,
        Long usuarioId,
        String usuarioNome,
        Instant createdAt,
        Instant updatedAt
) {}
