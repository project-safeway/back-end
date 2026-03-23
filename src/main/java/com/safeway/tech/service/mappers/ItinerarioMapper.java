package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.itinerario.ItinerarioAlunoResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioEscolaResponse;
import com.safeway.tech.api.dto.itinerario.ItinerarioResponse;
import com.safeway.tech.domain.models.Itinerario;
import com.safeway.tech.domain.models.ItinerarioAluno;
import com.safeway.tech.domain.models.ItinerarioEscola;

public class ItinerarioMapper {

    public static ItinerarioResponse toResponse(Itinerario itinerario) {
        return new ItinerarioResponse(
                itinerario.getId(),
                itinerario.getNome(),
                itinerario.getHorarioInicio(),
                itinerario.getHorarioFim(),
                itinerario.getTipoViagem(),
                itinerario.getAtivo(),
                itinerario.getAlunos().stream().map(ItinerarioMapper::toAlunoResponse).toList(),
                itinerario.getEscolas().stream().map(ItinerarioMapper::toEscolaResponse).toList()
        );
    }

    public static ItinerarioAlunoResponse toAlunoResponse(ItinerarioAluno itinerarioAluno) {
        return new ItinerarioAlunoResponse(
                itinerarioAluno.getAluno().getId(),
                itinerarioAluno.getAluno().getNome(),
                itinerarioAluno.getOrdemEmbarque(),
                itinerarioAluno.getEndereco().getId(),
                itinerarioAluno.getOrdemGlobal(),
                itinerarioAluno.getAluno().getEscola().getNome(),
                itinerarioAluno.getAluno().getResponsaveis().getFirst().getNome(),
                itinerarioAluno.getAluno().getSala()
        );
    }

    public static ItinerarioEscolaResponse toEscolaResponse(ItinerarioEscola itinerarioEscola) {
        return new ItinerarioEscolaResponse(
                itinerarioEscola.getEscola().getId(),
                itinerarioEscola.getEscola().getNome(),
                itinerarioEscola.getEndereco().getCidade(),
                itinerarioEscola.getOrdemParada(),
                itinerarioEscola.getEndereco().getId(),
                itinerarioEscola.getOrdemGlobal()
        );
    }
}
