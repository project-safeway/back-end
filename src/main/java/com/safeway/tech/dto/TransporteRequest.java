package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record TransporteRequest(
        @NotBlank(message = "Placa é obrigatória")
        @Size(max = 10) String placa,
        @Size(max = 50) String modelo,
        @PositiveOrZero Integer capacidade) {
}
