package com.safeway.tech.dto;

import com.safeway.tech.enums.NivelEnsinoEnum;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Escola;
import java.util.List;

public record EscolaComAlunosResponse(
        Long id,
        String nome,
        NivelEnsinoEnum nivelEnsino,
        EnderecoResponse endereco,
        List<AlunoResumoResponse> alunos
) {
    public static EscolaComAlunosResponse fromEntity(Escola escola) {
        return new EscolaComAlunosResponse(
                escola.getIdEscola(),
                escola.getNome(),
                escola.getNivelEnsino(),
                EnderecoResponse.fromEntity(escola.getEndereco()),
                escola.getAlunos().stream()
                        .map(AlunoResumoResponse::fromEntity)
                        .toList()
        );
    }

    public record AlunoResumoResponse(
            Long id,
            String nome,
            Integer serie,
            String sala,
            Boolean ativo
    ) {
        public static AlunoResumoResponse fromEntity(Aluno aluno) {
            return new AlunoResumoResponse(
                    aluno.getIdAluno(),
                    aluno.getNome(),
                    aluno.getSerie(),
                    aluno.getSala(),
                    aluno.getAtivo()
            );
        }
    }
}