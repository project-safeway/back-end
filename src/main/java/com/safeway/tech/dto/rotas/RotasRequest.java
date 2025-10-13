package com.safeway.tech.dto.rotas;

public record RotasRequest(
        Veiculo veiculo,
        PontosParada pontosParada,
        Boolean otimizarOrdem
) {
}
