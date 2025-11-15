package com.safeway.tech.dto;

import com.safeway.tech.models.Aluno;

import java.time.LocalDate;
import java.util.List;

public record AlunoResponse(
        String nome,
        String professor,
        LocalDate dtNascimento,
        Integer serie,
        String sala,
        EscolaResponse escola,
        List<ResponsavelResponse> responsaveis,
        Double valorPadraoMensalidade,
        Integer diaVencimento
        ) {

    public static AlunoResponse fromEntity(Aluno aluno) {
        List<ResponsavelResponse> responsaveis = aluno.getResponsaveis().stream()
                .map(ResponsavelResponse::fromEntity)
                .toList();

        return new AlunoResponse(
                aluno.getNome(),
                aluno.getProfessor(),
                aluno.getDtNascimento(),
                aluno.getSerie(),
                aluno.getSala(),
                EscolaResponse.fromEntity(aluno.getEscola()),
                responsaveis,
                aluno.getValorMensalidade(),
                aluno.getDiaVencimento()
        );
    }
}
