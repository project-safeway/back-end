package com.safeway.tech.repository;

import com.safeway.tech.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    @Query("SELECT a FROM Aluno a WHERE a.id = :idAluno AND a.usuario.id = :userId")
    Optional<Aluno> findByIdAlunoAndUsuario_IdUsuario(@Param("idAluno") UUID idAluno, @Param("userId") UUID userId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Aluno a WHERE a.escola.id = :idEscola AND a.usuario.id = :idUsuario")
    boolean existsByEscola_IdEscolaAndUsuario_IdUsuario(@Param("idEscola") UUID idEscola, @Param("idUsuario") UUID idUsuario);
}
