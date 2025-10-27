package com.safeway.tech.repository;

import com.safeway.tech.models.Itinerario;
import com.safeway.tech.models.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItinerarioRepository extends JpaRepository<Itinerario, Long> {
    List<Itinerario> findByTransporte(Transporte transporte);
}
