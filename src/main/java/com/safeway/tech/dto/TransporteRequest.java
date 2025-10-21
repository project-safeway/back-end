package com.safeway.tech.dto;

public record TransporteRequest(
        String placa,
        String modelo,
        Integer capacidade) {
}
