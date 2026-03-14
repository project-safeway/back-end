package com.safeway.tech.api.dto.aluno;

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
}
