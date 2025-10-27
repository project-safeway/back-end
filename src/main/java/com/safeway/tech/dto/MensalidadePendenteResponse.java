package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusPagamento;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MensalidadePendenteResponse(
        Long idMensalidade,
        Long idAluno,
        String nomeAluno,
        String nomeResponsavel,
        String telefoneResponsavel,
        String emailResponsavel,
        Integer mes,
        Integer ano,
        String mesAnoFormatado,
        BigDecimal valorMensalidade,
        LocalDate dataVencimento,
        Integer diasAtraso,
        StatusPagamento status
) {}