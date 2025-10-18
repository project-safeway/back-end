package com.safeway.tech.dto.rotas;

import java.util.List;

public record RotasResponse(
        Double distanciaTotal,
        Long tempoTotal,
        List<ParadaOtimizada> paradas,
        List<MetricasRota> metricas,
        String provedor
) {
}
