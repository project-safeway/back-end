package com.safeway.tech.api.dto.rotas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RotasRequest(
        Veiculo veiculo,
        List<PontoParada> pontosParada,
        Boolean otimizarOrdem
) {
}
