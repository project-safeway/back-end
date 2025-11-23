package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.List;

public record ItinerarioUpdateRequest(
        @NotBlank String nome,
        Time horarioInicio,
        Time horarioFim,
        @NotNull TipoViagemEnum tipoViagem,
        @NotNull Boolean ativo,
        List<@Valid ItinerarioAlunoRequest> alunos,
        List<ItinerarioParadaUpdate> paradas
        ) {
    public record ItinerarioParadaUpdate(
            String tipo,      // "ALUNO" ou "ESCOLA"
            Long id,          // alunoId ou escolaId
            Integer ordemGlobal,
            Integer ordemEspecifica // ordemEmbarque ou ordemVisita
    ) {}
}
