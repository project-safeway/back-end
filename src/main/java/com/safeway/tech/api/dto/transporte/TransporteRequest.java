package com.safeway.tech.api.dto.transporte;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransporteRequest(
        @NotBlank(message = "Placa é obrigatória")
        @Size(max = 10) String placa,
        @Size(max = 50) String modelo,
        @PositiveOrZero Integer capacidade) {
}
