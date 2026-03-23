package com.safeway.tech.auth.core.model;

public record IssuedToken(
        String value,
        long expiresIn
) {
}
