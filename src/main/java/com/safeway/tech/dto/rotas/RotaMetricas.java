package com.safeway.tech.dto.rotas;

public record RotaMetricas(
        String veiculoId,
        Double distanciaMetros,
        Long duracaoSegundos,
        Integer totalParadas,
        String horarioInicioRota,
        String horarioFimRota
) {
}
