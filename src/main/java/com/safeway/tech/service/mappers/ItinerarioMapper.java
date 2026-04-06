package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.itinerario.ItinerarioAlunoResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioEscolaResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioResponse;
import com.safeway.tech.domain.models.Itinerario;
import com.safeway.tech.domain.models.Responsavel;

import java.util.List;

public class ItinerarioMapper {

    public static ItinerarioResponse toResponse(Itinerario entity) {
        List<ItinerarioAlunoResponse> alunos = entity.getAlunos().stream()
                .map(a -> new ItinerarioAlunoResponse(
                        a.getAluno().getId(),
                        a.getAluno().getNome(),
                        a.getOrdemEmbarque(),
                        a.getEndereco() != null ? a.getEndereco().getId() : null,
                        a.getOrdemGlobal(),
                        a.getAluno().getEscola().getNome(),
                        a.getAluno().getResponsaveis().stream().map(Responsavel::getNome).findFirst().orElse(null),
                        a.getAluno().getSala()
                ))
                .toList();

        List<ItinerarioEscolaResponse> escolas = entity.getEscolas() != null
                ? entity.getEscolas().stream()
                    .map(ItinerarioEscolaResponse::fromEntity)
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
