package com.safeway.tech.api.dto.rotas;

public record MetricasRota(
        String idVeiculo,
        Double distanciaMetros,
        Long duracaoSegundos,
        Integer paradasRealizadas
) {
}
