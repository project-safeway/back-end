package com.safeway.tech.api.dto.evento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safeway.tech.domain.enums.EventoTypeEnum;
import com.safeway.tech.domain.enums.PriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EventoRequest(
        @NotBlank String title,
        String description,
        @NotNull LocalDate date,
        @NotNull EventoTypeEnum type,
        @NotNull PriorityEnum priority
) {}
