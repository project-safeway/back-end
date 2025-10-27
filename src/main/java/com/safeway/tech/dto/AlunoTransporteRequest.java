package com.safeway.tech.dto;

import com.safeway.tech.enums.TipoViagemEnum;

import java.util.Date;

public record AlunoTransporteRequest(
        Long idAluno,
        Long idTransporte,
        TipoViagemEnum tipoViagem,
        Date dataInicio,
        Date dataFim
) {
}
