package com.safeway.tech.dto;

import com.safeway.tech.models.Usuario;

import java.util.UUID;

public record UsuarioFeignResponse(
        UUID id,
        String nome,
        String email,
        Boolean ativo
) {

    public static UsuarioFeignResponse fromEntity(Usuario entity) {
        return new UsuarioFeignResponse(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getAtivo()
        );
    }
}
