package com.safeway.tech.api.dto.itinerario;

import java.util.UUID;

public record ItinerarioAlunoResponse(
        UUID alunoId,
        String nomeAluno,
        Integer ordemEmbarque,
        UUID enderecoId,
        Integer ordemGlobal,
        String nomeEscola,
        String nomeResponsavel,
        String sala
) {
}
