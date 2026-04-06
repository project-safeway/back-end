package com.safeway.tech.api.dto.transporte;

import com.safeway.tech.api.dto.aluno.AlunoResponse;

import java.util.List;
import java.util.UUID;

public record TransporteResponse(
        UUID idTransporte,
        String placa,
        String modelo,
        Integer capacidade,
        List<AlunoResponse> alunos
) {
}
