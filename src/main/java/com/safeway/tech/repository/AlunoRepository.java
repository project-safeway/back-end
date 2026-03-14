package com.safeway.tech.repository;

import com.safeway.tech.domain.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

    @Query("SELECT a FROM Aluno a WHERE a.id IN :ids AND a.usuario.id = :userId")
    List<Aluno> findByIdInAndIdUsuario(@Param("ids") List<UUID> ids, @Param("userId") UUID userId);

    @Query("SELECT a FROM Aluno a WHERE a.ativo = true AND a.usuario.id = :userId")
    List<Aluno> findByAtivoTrueAndIdUsuario(@Param("userId") UUID userId);

    @Query("SELECT a FROM Aluno a WHERE a.id = :alunoId AND a.usuario.id = :userId")
    Optional<Aluno> findByIdAndUsuarioId(@Param("alunoId") UUID alunoId, @Param("userId") UUID userId);
}
