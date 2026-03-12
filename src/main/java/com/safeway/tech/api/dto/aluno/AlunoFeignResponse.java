package com.safeway.tech.api.dto.aluno;

import com.safeway.tech.domain.models.Aluno;

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
