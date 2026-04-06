package com.safeway.tech.api.dto.evento;

import com.safeway.tech.domain.enums.EventoTypeEnum;
import com.safeway.tech.domain.enums.PriorityEnum;

import java.time.LocalDate;
import java.util.UUID;

public record EventoResponse(
        UUID id,
        String title,
        String description,
        LocalDate date,
        EventoTypeEnum type,
        PriorityEnum priority
) {
}
