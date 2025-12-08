package com.safeway.tech.dto;

import java.time.LocalDate;

public record PagamentoResponse(
        Long idPagamento,
        LocalDate dataPagamento,
        Double valorPagamento,
        FuncionarioResponse funcionario
) {

    public static PagamentoResponse fromEntity(com.safeway.tech.models.Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getIdPagamento(),
                pagamento.getDataPagamento(),
                pagamento.getValorPagamento(),
                FuncionarioResponse.fromEntity(pagamento.getFuncionario())
        );
    }
}
