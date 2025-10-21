package com.safeway.tech.services;

import com.safeway.tech.dto.AlunoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    // Todo verificar de quem é a responsabilidade de portar os métodos de buscarAlunosPorEscola, buscarAlunosPorResponsavel, etc.
    // Isso vale para todas as entidades do sistema. Não apenas relacionadas a Aluno.

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ResponsavelService responsavelService;

    public Aluno cadastrarAluno(AlunoRequest request) {
        Responsavel responsavel = responsavelService.getById(request.idResponsavel());

        Aluno aluno = new Aluno();

        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setResponsavel(responsavel);

        return alunoRepository.save(aluno);
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com o ID: " + id));
    }

    public Aluno atualizarAluno(Long id, AlunoRequest request) {
        Aluno aluno = buscarAlunoPorId(id);

        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());

        return alunoRepository.save(aluno);
    }

    public void deletarAluno(Long id) {
        alunoRepository.deleteById(id);
    }
}
