package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

public record PagamentoRequest(
        @NotNull Long idFuncionario,
        @NotNull @PastOrPresent Date dataPagamento,
        @NotNull @Positive BigDecimal valorPagamento
) {
}
