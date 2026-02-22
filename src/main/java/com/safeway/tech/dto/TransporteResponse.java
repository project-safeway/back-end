package com.safeway.tech.dto;

import com.safeway.tech.models.Transporte;

import java.util.List;
import java.util.UUID;

public record TransporteResponse(
        UUID idTransporte,
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
                transporte.getId(),
                transporte.getPlaca(),
                transporte.getModelo(),
                transporte.getCapacidade(),
                alunos
        );
    }
}
