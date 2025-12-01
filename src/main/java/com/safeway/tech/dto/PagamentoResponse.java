package com.safeway.tech.dto;

import com.safeway.tech.models.Pagamento;

import java.time.LocalDate;

public record PagamentoResponse(
        Long idPagamento,
        LocalDate dataPagamento,
        Double valorPagamento,
        String descricao
) {

    public static PagamentoResponse fromEntity(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getIdPagamento(),
                pagamento.getDataPagamento(),
                pagamento.getValorPagamento(),
                pagamento.getDescricao()
        );
    }
}
