package com.safeway.tech.dto.rotas;

public record Veiculo(
    String id,
    Localizacao localizacaoInicial,
    Localizacao localizacaoFinal
) {
}
