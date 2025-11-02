package com.safeway.tech.repository;

import com.safeway.tech.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByAtivoTrue();

    List<Aluno> findByUsuario_IdUsuario(Long userId);

    List<Aluno> findByUsuario_IdUsuarioAndAtivoTrue(Long userId);

    Optional<Aluno> findByIdAlunoAndUsuario_IdUsuario(Long idAluno, Long userId);
}
