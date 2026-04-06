package com.safeway.tech.service.services;

import com.safeway.tech.api.dto.rotas.RotasRequest;
import com.safeway.tech.api.dto.rotas.RotasResponse;

public interface RotasService {
    RotasResponse otimizarRota(RotasRequest request);
    String nomeProvedor();
}
