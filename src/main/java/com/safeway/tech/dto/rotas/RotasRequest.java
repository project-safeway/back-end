package com.safeway.tech.dto.rotas;

import java.util.List;

public record RotasRequest(
        Veiculo veiculo,
        List<PontoParada> pontosParada,
        Boolean otimizarOrdem
) {
}
