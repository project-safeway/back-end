package com.safeway.tech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record PagamentoRequest(
        @NotNull @PastOrPresent LocalDate dataPagamento,
        @NotNull @Positive Double valorPagamento,
        @NotBlank String descricao
) {
}
