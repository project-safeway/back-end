package com.safeway.tech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public record PagamentoRequest(
        @NotNull @PastOrPresent LocalDate dataPagamento,
        @NotNull @Positive Double valorPagamento
) {
}
