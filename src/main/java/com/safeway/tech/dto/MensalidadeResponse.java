package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusPagamento;

import java.time.LocalDate;

public record MensalidadeResponse(
        Long idMensalidade,
        String aluno,
        String responsavel,
        LocalDate dataVencimento,
        LocalDate dataPagemento,
        Double valorMensalidade,
        Double valorPagamento,
        StatusPagamento status
) {}