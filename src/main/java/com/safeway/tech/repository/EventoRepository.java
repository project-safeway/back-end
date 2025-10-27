package com.safeway.tech.repository;

import com.safeway.tech.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("select e from Evento e " +
           "where e.clientId = :clientId " +
           "and (:start is null or e.date >= :start) " +
           "and (:end is null or e.date <= :end) " +
           "and (:type is null or lower(e.type) = lower(:type)) " +
           "and (:priority is null or lower(e.priority) = lower(:priority))")
    List<Evento> findFiltrado(@Param("clientId") Long clientId,
                              @Param("start") LocalDate start,
                              @Param("end") LocalDate end,
                              @Param("type") String type,
                              @Param("priority") String priority);
}
