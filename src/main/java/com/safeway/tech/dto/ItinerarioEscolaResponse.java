package com.safeway.tech.dto;

import com.safeway.tech.models.ItinerarioEscola;

public record ItinerarioEscolaResponse(
        Long escolaId,
        String nome,
        String cidade,
        Integer ordemVisita,
        Long enderecoId,
        Integer ordemGlobal
) {
    public static ItinerarioEscolaResponse fromEntity(ItinerarioEscola entity) {
        return new ItinerarioEscolaResponse(
                entity.getEscola().getIdEscola(),
                entity.getEscola().getNome(),
                entity.getEndereco().getCidade(),
                entity.getOrdemParada(),
                entity.getEndereco().getIdEndereco(),
                entity.getOrdemGlobal()
        );
    }
}
