package com.safeway.tech.api.dto.transporte;

import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.Responsavel;

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
            Responsavel r = a.getResponsaveis().getFirst();
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
