package com.safeway.tech.auth.core.model;

public record RegisterAuthUserData(
        String nome,
        String email,
        String passwordHash,
        String telefone,
        String transportePlaca,
        String transporteModelo,
        Integer transporteCapacidade
) {
}

