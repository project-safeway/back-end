package com.safeway.tech.services;

import com.safeway.tech.dto.rotas.RotasRequest;
import com.safeway.tech.dto.rotas.RotasResponse;

public interface RotasService {
    RotasResponse otimizarRota(RotasRequest request);
    String nomeProvedor();
}
