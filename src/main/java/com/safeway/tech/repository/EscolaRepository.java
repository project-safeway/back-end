package com.safeway.tech.repository;

import com.safeway.tech.models.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EscolaRepository extends JpaRepository<Escola,UUID> {
    @Query("SELECT e FROM Escola e WHERE e.usuario.id = :usuarioId")
    List<Escola> findByUsuarioIdUsuario(@Param("usuarioId") UUID usuarioId);

    @Query("SELECT e FROM Escola e WHERE e.id = :idEscola AND e.usuario.id = :userId")
    Optional<Escola> findByIdEscolaAndUsuario_IdUsuario(@Param("idEscola") UUID idEscola, @Param("userId") UUID userId);
}
