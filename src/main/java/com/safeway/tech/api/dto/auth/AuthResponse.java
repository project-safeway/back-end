package com.safeway.tech.api.dto.auth;

import java.util.UUID;

public record AuthResponse(
        String accessToken,
        Long expiresIn,
        String nomeUsuario,
        UUID idTransporte
) {
}