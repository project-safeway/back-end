package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.aluno.AlunoResponse;
import com.safeway.tech.api.dto.transporte.TransporteResponse;
import com.safeway.tech.domain.models.Transporte;

import java.util.List;

public class TransporteMapper {

    public static TransporteResponse toResponse(Transporte transporte) {
        List<AlunoResponse> alunos = null;
        if (transporte.getAlunosTransportes() != null) {
            alunos = transporte.getAlunosTransportes().stream()
                    .map(AlunoMapper::toResponse)
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
