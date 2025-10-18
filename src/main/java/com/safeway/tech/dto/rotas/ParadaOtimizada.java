package com.safeway.tech.dto.rotas;

public record ParadaOtimizada(
        String idParada,
        Localizacao localizacao,
        String horarioChegada,
        Double distanciaAteAqui,
        Long tempoViagem
) {
}
