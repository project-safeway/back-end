package com.safeway.tech.dto.rotas;

public record Veiculo(
    String id,
    Coordenada localizacaoInicial,
    Coordenada localizacaoFinal
) {
}
