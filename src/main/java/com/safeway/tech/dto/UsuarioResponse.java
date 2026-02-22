package com.safeway.tech.dto;

import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        String tel1,
        String tel2,
        TransporteResponse transporte,
        String role
) {
    public static UsuarioResponse fromEntity(com.safeway.tech.models.Usuario u) {
        return new UsuarioResponse(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getTel1(),
                u.getTel2(),
                u.getTransporte() != null ? TransporteResponse.fromEntity(u.getTransporte()) : null,
                u.getRole() != null ? u.getRole().name() : null
        );
    }
}

