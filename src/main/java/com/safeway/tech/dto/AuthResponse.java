package com.safeway.tech.dto;

import java.util.UUID;

public record AuthResponse(
        String accessToken,
        Long expiresIn,
        String nomeUsuario,
        UUID idTransporte
) {
}