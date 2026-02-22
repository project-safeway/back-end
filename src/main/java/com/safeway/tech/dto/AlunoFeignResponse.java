package com.safeway.tech.dto;

import com.safeway.tech.models.Aluno;

import java.util.UUID;

public record AlunoFeignResponse(
        UUID id,
        String nome,
        Double valorMensalidade,
        Integer diaVencimento,
        Boolean ativo,
        UsuarioResponse usuario
) {
    public record UsuarioResponse(UUID idUsuario) {}

    public static AlunoFeignResponse fromEntity(Aluno entity) {
        return new AlunoFeignResponse(
                entity.getId(),
                entity.getNome(),
                entity.getValorMensalidade(),
                entity.getDiaVencimento(),
                entity.getAtivo(),
                new UsuarioResponse(entity.getUsuario().getId())
        );
    }
}
