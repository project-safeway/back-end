package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.itinerario.ItinerarioAlunoResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioEscolaResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioResponse;
import com.safeway.tech.domain.models.Itinerario;
import com.safeway.tech.domain.models.Responsavel;

import java.util.List;

public class ItinerarioMapper {

    public static ItinerarioResponse toResponse(Itinerario itinerario) {
        List<ItinerarioAlunoResponse> alunos = itinerario.getAlunos().stream()
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

        List<ItinerarioEscolaResponse> escolas = itinerario.getEscolas() != null
                ? itinerario.getEscolas().stream()
                    .map(ItinerarioEscolaResponse::fromEntity)
                    .toList()
                : List.of();

        return new ItinerarioResponse(
                itinerario.getId(),
                itinerario.getNome(),
                itinerario.getHorarioInicio(),
                itinerario.getHorarioFim(),
                itinerario.getTipoViagem(),
                itinerario.getAtivo(),
                alunos,
                escolas
        );
    }
}
