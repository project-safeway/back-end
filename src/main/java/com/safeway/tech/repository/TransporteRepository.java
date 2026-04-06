package com.safeway.tech.repository;

import com.safeway.tech.domain.models.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransporteRepository extends JpaRepository<Transporte, UUID> {

    @Query("SELECT t FROM Transporte t WHERE t.placa = :placa")
    Optional<Transporte> findByPlaca(@Param("placa") String placa);

    @Query("SELECT t FROM Transporte t WHERE t.id = :idTransporte AND t.usuario.id = :userId")
    Optional<Transporte> findByIdTransporteAndIdUsuario(UUID idTransporte, @Param("userId") UUID userId);

    @Query("SELECT t FROM Transporte t WHERE t.usuario.id = :userId")
    List<Transporte> findAllByIdUsuario(@Param("userId") UUID userId);
}
