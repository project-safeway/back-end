package com.safeway.tech.dto;

public record AuthResponse(
        String accessToken,
        Long expiresIn,
        String nomeUsuario,
        Long idTransporte
) {
}