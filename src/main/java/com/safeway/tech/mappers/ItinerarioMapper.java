package com.safeway.tech.mappers;

import com.safeway.tech.dto.ItinerarioAlunoResponse;
import com.safeway.tech.dto.ItinerarioEscolaResponse;
import com.safeway.tech.dto.ItinerarioResponse;
import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Responsavel;

import java.util.List;

public class ItinerarioMapper {

    public static ItinerarioResponse toResponse(Itinerario entity) {
        List<ItinerarioAlunoResponse> alunos = entity.getAlunos().stream()
                .map(a -> new ItinerarioAlunoResponse(
                        a.getAluno().getIdAluno(),
                        a.getAluno().getNome(),
                        a.getOrdemEmbarque(),
                        a.getEndereco() != null ? a.getEndereco().getIdEndereco() : null,
                        a.getOrdemGlobal(),
                        a.getAluno().getEscola().getNome(),
                        a.getAluno().getResponsaveis().stream().map(Responsavel::getNome).findFirst().orElse(null),
                        a.getAluno().getSala()
                ))
                .toList();

        List<ItinerarioEscolaResponse> escolas = entity.getEscolas() != null
                ? entity.getEscolas().stream()
                    .map(e -> ItinerarioEscolaResponse.fromEntity(e))
                    .toList()
                : List.of();

        return new ItinerarioResponse(
                entity.getId(),
                entity.getNome(),
                entity.getHorarioInicio(),
                entity.getHorarioFim(),
                entity.getTipoViagem(),
                entity.getAtivo(),
                alunos,
                escolas
        );
    }
}
