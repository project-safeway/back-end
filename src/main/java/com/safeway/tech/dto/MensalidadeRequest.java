package com.safeway.tech.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public record MensalidadeRequest(
        @NotNull @Positive Long idAlunoTransporte,
        @NotNull @PastOrPresent Date dataMensalidade,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal valorMensalidade

) {
}
