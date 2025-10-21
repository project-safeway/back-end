package com.safeway.tech.dto;

import java.math.BigDecimal;
import java.util.Date;

public record MensalidadeRequest(
        Long idAlunoTransporte,
        Date dataMensalidade,
        BigDecimal valorMensalidade

) {
}
