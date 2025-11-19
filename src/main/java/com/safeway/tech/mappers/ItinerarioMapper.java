package com.safeway.tech.mappers;

import com.safeway.tech.dto.ItinerarioAlunoResponse;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.models.Itinerario;

import java.util.List;

public class ItinerarioMapper {

    public static ItinerarioResponse toResponse(Itinerario entity) {
        List<ItinerarioAlunoResponse> alunos = entity.getAlunos().stream()
                .map(a -> new ItinerarioAlunoResponse(
                        a.getAluno().getIdAluno(),
                        a.getAluno().getNome(),
                        a.getOrdemEmbarque(),
                        a.getEndereco() != null ? a.getEndereco().getIdEndereco() : null
                ))
                .toList();

        return new ItinerarioResponse(
                entity.getId(),
                entity.getNome(),
                entity.getHorarioInicio(),
                entity.getHorarioFim(),
                entity.getTipoViagem(),
                entity.getAtivo(),
                alunos
        );
    }

}
