package com.safeway.tech.auth.core.application;

public record RegisterTransporteCommand(
        String placa,
        String modelo,
        Integer capacidade
) {
}
