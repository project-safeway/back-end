package com.safeway.tech.auth.core.application;

public record RegisterUserCommand(
        String nome,
        String email,
        String senha,
        String telefone,
        RegisterTransporteCommand transporte
) {
}
