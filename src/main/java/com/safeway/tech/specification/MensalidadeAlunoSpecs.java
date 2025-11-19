package com.safeway.tech.specification;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.MensalidadeAluno;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MensalidadeAlunoSpecs {

    public static Specification<MensalidadeAluno> comAluno(Long alunoId) {
        return (root, query, cb) ->
                alunoId == null ? null : cb.equal(root.get("aluno").get("idAluno"), alunoId);
    }

    public static Specification<MensalidadeAluno> comStatus(List<StatusPagamento> status) {
        return (root, query, cb) ->
                (status == null || status.isEmpty())
                        ? null
                        : root.get("status").in(status);
    }

    public static Specification<MensalidadeAluno> comPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return (root, query, cb) -> {

            if (dataInicio == null && dataFim == null) {
                return null;
            }

            if (dataInicio != null && dataFim != null) {
                return cb.between(root.get("dataVencimento"), dataInicio, dataFim);
            }

            if (dataInicio != null) {
                return cb.greaterThanOrEqualTo(root.get("dataVencimento"), dataInicio);
            }

            return cb.lessThanOrEqualTo(root.get("dataVencimento"), dataFim);
        };
    }

    public static Specification<MensalidadeAluno> comUsuario(Long usuarioId) {
        return (root, query, cb) ->
                usuarioId == null ? null : cb.equal(root.get("aluno").get("usuario").get("idUsuario"), usuarioId);
    }
}
