package com.safeway.tech.api.dto.rotas;

public record ParadaOtimizada(
        String idParada,
        Localizacao localizacao,
        String horarioChegada,
        Double distanciaAteAqui,
        Long tempoViagem
) {
}
