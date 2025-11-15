package com.safeway.tech.dto;

import java.time.LocalDate;

public record PagamentoResponse(
        LocalDate dataPagamento,
        Double valorPagamento,
        FuncionarioResponse funcionario
) {

    public static PagamentoResponse fromEntity(com.safeway.tech.models.Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getDataPagamento(),
                pagamento.getValorPagamento(),
                FuncionarioResponse.fromEntity(pagamento.getFuncionario())
        );
    }
}
