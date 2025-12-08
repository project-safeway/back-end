package com.safeway.tech.dto.rotas;

public record PontoParada(
        String id,
        Localizacao localizacao,
        Integer ordem // ordem opcional enviada pelo front; pode ser null
) {
}
