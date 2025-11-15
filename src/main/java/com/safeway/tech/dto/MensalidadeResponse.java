package com.safeway.tech.dto;

import com.safeway.tech.enums.StatusPagamento;
import com.safeway.tech.models.MensalidadeAluno;

import java.time.LocalDate;
import java.util.List;

public record MensalidadeResponse(
        Long idMensalidade,
        AlunoResponse aluno,
        LocalDate dataVencimento,
        LocalDate dataPagemento,
        Double valorMensalidade,
        Double valorPagamento,
        StatusPagamento status
) {

    public static MensalidadeResponse fromEntity(MensalidadeAluno mensalidade) {
        return new MensalidadeResponse(
                mensalidade.getId(),
                AlunoResponse.fromEntity(mensalidade.getAluno()),
                mensalidade.getDataVencimento(),
                mensalidade.getDataPagamento(),
                mensalidade.getValorMensalidade(),
                mensalidade.getValorPago(),
                mensalidade.getStatus()
        );
    }

}