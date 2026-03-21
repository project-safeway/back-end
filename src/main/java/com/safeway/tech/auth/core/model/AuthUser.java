package com.safeway.tech.auth.core.model;

import java.util.UUID;

public record AuthUser(
        UUID id,
        String nome,
        String email,
        String passwordHash,
        String role,
        UUID idTransporte
) {
}
