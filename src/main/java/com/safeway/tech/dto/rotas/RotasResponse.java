package com.safeway.tech.dto.rotas;

public record RotasResponse(
        Double distanciaTotal,
        Long duracaoTotalSegundos,
        String processador
) {
}
