package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.util.Date;

public record AlunoTransporteRequest(
        @NotNull @Positive Long idAluno,
        @NotNull @Positive Long idTransporte,
        @NotNull TipoViagemEnum tipoViagem,
        @NotNull @PastOrPresent Date dataInicio,
        Date dataFim
) {
}
