package com.safeway.tech.api.dto.rotas;

public record PontoParada(
        String id,
        Localizacao localizacao,
        Integer ordem // ordem opcional enviada pelo front; pode ser null
) {
}
