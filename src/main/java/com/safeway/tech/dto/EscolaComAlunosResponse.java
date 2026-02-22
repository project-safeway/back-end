package com.safeway.tech.dto;

import com.safeway.tech.enums.NivelEnsinoEnum;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Escola;
import java.util.List;
import java.util.UUID;

public record EscolaComAlunosResponse(
        UUID id,
        String nome,
        NivelEnsinoEnum nivelEnsino,
        EnderecoResponse endereco,
        List<AlunoResumoResponse> alunos
) {
    public static EscolaComAlunosResponse fromEntity(Escola escola) {
        return new EscolaComAlunosResponse(
                escola.getId(),
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoResponse.fromEntity(escola.getEndereco()),
                escola.getAlunos().stream()
                        .map(AlunoResumoResponse::fromEntity)
                        .toList()
        );
    }

    public record AlunoResumoResponse(
            UUID id,
            String nome,
            Integer serie,
            String sala,
            Boolean ativo
    ) {
        public static AlunoResumoResponse fromEntity(Aluno aluno) {
            return new AlunoResumoResponse(
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getSerie(),
                    aluno.getSala(),
                    aluno.getAtivo()
            );
        }
    }
}