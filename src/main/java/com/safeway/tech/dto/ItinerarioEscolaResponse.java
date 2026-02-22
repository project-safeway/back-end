package com.safeway.tech.dto;

import com.safeway.tech.models.ItinerarioEscola;

import java.util.UUID;

public record ItinerarioEscolaResponse(
        UUID escolaId,
        String nome,
        String cidade,
        Integer ordemVisita,
        UUID enderecoId,
        Integer ordemGlobal
) {
    public static ItinerarioEscolaResponse fromEntity(ItinerarioEscola entity) {
        return new ItinerarioEscolaResponse(
                entity.getEscola().getId(),
                entity.getEscola().getNome(),
                entity.getEndereco().getCidade(),
                entity.getOrdemParada(),
                entity.getEndereco().getId(),
                entity.getOrdemGlobal()
        );
    }
}
