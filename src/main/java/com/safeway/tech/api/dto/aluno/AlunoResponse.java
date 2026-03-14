package com.safeway.tech.api.dto.aluno;

import com.safeway.tech.api.dto.escola.EscolaResumeResponse;
import com.safeway.tech.api.dto.responsavel.ResponsavelResponse;

import java.time.LocalDate;
import java.util.List;

public record AlunoResponse(
        String nome,
        String professor,
        LocalDate dtNascimento,
        Integer serie,
        String sala,
        EscolaResumeResponse escola,
        List<ResponsavelResponse> responsaveis,
        Double valorPadraoMensalidade,
        Integer diaVencimento
) {
}
