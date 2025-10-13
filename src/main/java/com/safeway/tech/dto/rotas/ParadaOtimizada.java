package com.safeway.tech.dto.rotas;

public record ParadaOtimizada(
        String servicoId,
        Coordenada localizacao,
        String horarioChegada,
        String horarioSaida,
        Integer esperaSegundos,
        Double distanciaAnteriorMetros,
        Long tempoViagemAnteriorSegundos
) {
}
