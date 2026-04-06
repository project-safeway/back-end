package com.safeway.tech.api.dto.transporte;

import com.safeway.tech.api.dto.aluno.AlunoResponse;
import com.safeway.tech.domain.models.Transporte;

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
