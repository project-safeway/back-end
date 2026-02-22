package com.safeway.tech.repository;

import com.safeway.tech.models.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResponsavelRepository extends JpaRepository<Responsavel, UUID> {

    @Query("SELECT r FROM Responsavel r WHERE r.usuario.id = :userId")
    List<Responsavel> findAllByUsuario_IdUsuario(@Param("userId") UUID userId);

    @Query("SELECT r FROM Responsavel r JOIN r.alunos a WHERE a.id = :alunoId AND r.usuario.id = :usuarioId")
    List<Responsavel> findByAlunosIdAlunoAndUsuarioIdUsuario(@Param("alunoId") UUID alunoId, @Param("usuarioId") UUID usuarioId);

    @Query("SELECT r FROM Responsavel r WHERE r.id = :idResponsavel AND r.usuario.id = :usuarioId")
    Optional<Responsavel> findByIdResponsavelAndUsuario_IdUsuario(@Param("idResponsavel") UUID idResponsavel, @Param("usuarioId") UUID usuarioId);

    @Query("SELECT r FROM Responsavel r WHERE r.cpf = :cpf AND r.usuario.id = :usuarioId")
    Optional<Responsavel> findByCpfAndUsuario_IdUsuario(@Param("cpf") String cpf, @Param("usuarioId") UUID usuarioId);
}
