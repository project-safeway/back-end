package com.safeway.tech.dto.rotas;

public record MetricasRota(
        String idVeiculo,
        Double distanciaMetros,
        Long duracaoSegundos,
        Integer paradasRealizadas
) {
}
