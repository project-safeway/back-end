package com.safeway.tech.dto;

import java.util.Date;

public record AlunoRequest(
        Long idResponsavel,
        String nome,
        String professor,
        Date dtNascimento,
        Integer serie,
        String sala
) {
}
