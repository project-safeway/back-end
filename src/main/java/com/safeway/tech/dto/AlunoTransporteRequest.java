package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;
import java.util.UUID;

public record AlunoTransporteRequest(
        @NotNull UUID idAluno,
        @NotNull UUID idTransporte,
        @NotNull TipoViagemEnum tipoViagem,
        @NotNull @PastOrPresent Date dataInicio,
        Date dataFim
) {
}
