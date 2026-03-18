package com.safeway.tech.auth.infrastructure.entrypoint.dto;

public record ServiceTokenResponseV2(
        String accessToken,
        Long expiresIn
) {
}

