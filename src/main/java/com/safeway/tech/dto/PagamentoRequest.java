package com.safeway.tech.dto;

import java.math.BigDecimal;

public record PagamentoRequest(
        Long idFuncionario,
        String dataPagamento,
        BigDecimal valorPagamento
) {
}
