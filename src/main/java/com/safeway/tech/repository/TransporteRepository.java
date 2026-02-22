package com.safeway.tech.repository;

import com.safeway.tech.models.Transporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransporteRepository extends JpaRepository<Transporte, UUID> {

    @Query("SELECT t FROM Transporte t WHERE t.placa = :placa")
    Optional<Transporte> findByPlaca(@Param("placa") String placa);

    @Query("SELECT t FROM Transporte t WHERE t.placa = :placa AND t.usuario.id = :userId")
    Optional<Transporte> findByPlacaAndUsuario_IdUsuario(@Param("placa") String placa, @Param("userId") UUID userId);

    @Query("SELECT t FROM Transporte t WHERE t.id = :idTransporte AND t.usuario.id = :userId")
    Optional<Transporte> findByIdTransporteAndUsuario_IdUsuario(UUID idTransporte, @Param("userId") UUID userId);

    @Query("SELECT t FROM Transporte t WHERE t.usuario.id = :userId")
    List<Transporte> findAllByUsuario_IdUsuario(@Param("userId") UUID userId);
}
