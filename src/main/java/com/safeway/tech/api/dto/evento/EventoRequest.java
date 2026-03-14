package com.safeway.tech.api.dto.evento;

import com.safeway.tech.domain.enums.EventoTypeEnum;
import com.safeway.tech.domain.enums.PriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record EventoRequest(
        UUID id,
        @NotBlank String title,
        String description,
        @NotNull LocalDate date,
        @NotNull EventoTypeEnum type,
        @NotNull PriorityEnum priority,
        UUID usuarioId,
        String usuarioNome,
        Instant createdAt,
        Instant updatedAt
) {}
