package com.safeway.tech.api.dto.rotas;

import java.util.List;

public record RotasRequest(
        Veiculo veiculo,
        List<PontoParada> pontosParada,
        Boolean otimizarOrdem
) {
}
