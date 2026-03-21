package com.safeway.tech.auth.infrastructure.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;

public record ServiceTokenRequestV2(
        @NotBlank String clientId,
        @NotBlank String clientSecret
) {
}

