package com.safeway.tech.repository;

import com.safeway.tech.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {

    @Query("select e from Evento e " +
           "where e.usuario.id = :userId " +
           "and (:start is null or e.date >= :start) " +
           "and (:end is null or e.date <= :end) " +
           "and (:type is null or lower(e.type) = lower(:type)) " +
           "and (:priority is null or lower(e.priority) = lower(:priority))")
    List<Evento> findFiltrado(@Param("userId") UUID userId,
                              @Param("start") LocalDate start,
                              @Param("end") LocalDate end,
                              @Param("type") String type,
                              @Param("priority") String priority);

    @Query("SELECT e FROM Evento e WHERE e.id = :idEvento AND e.usuario.id = :userId")
    Optional<Evento> findByIdAndUsuario_IdUsuario(@Param("idEvento") UUID idEvento, @Param("userId") UUID userId);

    @Query("SELECT e FROM Evento e WHERE e.usuario.id = :userId")
    List<Evento> findAllByUsuario_IdUsuario(@Param("userId") UUID userId);
}
