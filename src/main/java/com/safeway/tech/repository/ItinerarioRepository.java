package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ItinerarioRepository extends JpaRepository<Itinerario, UUID> {

    @Query("SELECT i FROM Itinerario i WHERE i.transporte.id = :idTransporte")
    List<Itinerario> findAllByTransporte(@Param("idTransporte") UUID idTransporte);
}
