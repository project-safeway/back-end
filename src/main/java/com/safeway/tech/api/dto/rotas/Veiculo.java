package com.safeway.tech.api.dto.rotas;

public record Veiculo(
        String id,
        Localizacao localizacaoInicial,
        Localizacao localizacaoFinal
) {
}
