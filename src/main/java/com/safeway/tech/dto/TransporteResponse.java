package com.safeway.tech.dto;

import com.safeway.tech.models.Transporte;

import java.util.List;

public record TransporteResponse(
        String placa,
        String modelo,
        Integer capacidade,
        List<AlunoResponse> alunos
) {

    public static TransporteResponse fromEntity(Transporte transporte) {
        List<AlunoResponse> alunos = null;
        if (transporte.getAlunosTransportes() != null) {
            alunos = transporte.getAlunosTransportes().stream()
                    .map(AlunoResponse::fromEntity)
                    .toList();
        }

        return new TransporteResponse(
                transporte.getPlaca(),
                transporte.getModelo(),
                transporte.getCapacidade(),
                alunos
        );
    }
}
