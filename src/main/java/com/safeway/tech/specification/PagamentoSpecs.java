package com.safeway.tech.specification;

import com.safeway.tech.models.Pagamento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PagamentoSpecs {

    public static Specification<Pagamento> comDescricao(String descricao) {
        return (root, query, cb) ->
                descricao == null ? null : cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%");
    }

    public static Specification<Pagamento> comPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, cb) -> {

            if (dataInicio == null && dataFim == null) {
                return null;
            }

            if (dataInicio != null && dataFim != null) {
                return cb.between(root.get("dataPagamento"), dataInicio, dataFim);
            }

            if (dataInicio != null) {
                return cb.greaterThanOrEqualTo(root.get("dataPagamento"), dataInicio);
            }

            return cb.lessThanOrEqualTo(root.get("dataPagamento"), dataFim);
        };
    }

    public static Specification<Pagamento> comValor(Double valorMinimo, Double valorMaximo) {
        return (root, query, cb) -> {

            if (valorMinimo == null && valorMaximo == null) {
                return null;
            }

            if (valorMinimo != null && valorMaximo != null) {
                return cb.between(root.get("valorPagamento"), valorMinimo, valorMaximo);
            }

            if (valorMinimo != null) {
                return cb.greaterThanOrEqualTo(root.get("valorPagamento"), valorMinimo);
            }

            return cb.lessThanOrEqualTo(root.get("valorPagamento"), valorMaximo);
        };
    }

    public static Specification<Pagamento> comUsuario(Long usuarioId) {
        return (root, query, cb) ->
                usuarioId == null ? null : cb.equal(root.get("usuario").get("idUsuario"), usuarioId);
    }

}
