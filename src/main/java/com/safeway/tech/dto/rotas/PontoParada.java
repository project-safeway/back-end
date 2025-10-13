package com.safeway.tech.dto.rotas;

public record PontoParada(
        String id,
        Coordenada localizacao,
        Integer duracaoSegundos
) {
}
