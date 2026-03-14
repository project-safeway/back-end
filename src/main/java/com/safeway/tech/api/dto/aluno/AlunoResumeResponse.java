package com.safeway.tech.api.dto.aluno;

import java.util.UUID;

public record AlunoResumeResponse(
        UUID id,
        String nome,
        Integer serie,
        String sala,
        Boolean ativo
) {
}
