package com.safeway.tech.repository;

import com.safeway.tech.models.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {
    Optional<Transporte> findByPlaca(String placa);
}
