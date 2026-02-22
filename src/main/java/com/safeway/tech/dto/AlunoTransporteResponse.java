package com.safeway.tech.dto;

import com.safeway.tech.models.Aluno;

import java.util.UUID;

public record AlunoTransporteResponse(
        UUID idAluno,
        String nome,
        String escola,
        String nomeResponsavel
) {
    public static AlunoTransporteResponse fromEntity(Aluno a) {
        String nomeEscola = null;
        if (a.getEscola() != null) nomeEscola = a.getEscola().getNome();

        String nomeResp = null;
        if (a.getResponsaveis() != null && !a.getResponsaveis().isEmpty()) {
            com.safeway.tech.models.Responsavel r = a.getResponsaveis().getFirst();
            if (r != null) nomeResp = r.getNome();
        }

        return new AlunoTransporteResponse(
                a.getId(),
                a.getNome(),
                nomeEscola,
                nomeResp
        );
    }
}
