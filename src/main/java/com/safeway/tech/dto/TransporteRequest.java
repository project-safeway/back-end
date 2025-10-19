package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;

public record TransporteRequest(
        @NotBlank(message = "Placa é obrigatória")
        String placa,
        String modelo,
        Integer capacidade) {
}
