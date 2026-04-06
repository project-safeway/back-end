package com.safeway.tech.api.dto.transporte;

import java.util.UUID;

public record AlunoTransporteResponse(
        UUID idAluno,
        String nome,
        String escola,
        String nomeResponsavel
) {
}
