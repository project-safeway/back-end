package com.safeway.tech.repository;

import com.safeway.tech.models.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {
    Optional<Transporte> findByPlaca(String placa);

    Optional<Transporte> findByPlacaAndUsuario_IdUsuario(String placa, Long userId);

    Optional<Transporte> findByIdTransporteAndUsuario_IdUsuario(Long idTransporte, Long userId);

    List<Transporte> findAllByUsuario_IdUsuario(Long userId);
}
