package com.safeway.tech.repository;

import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {
    Optional<Transporte> findByPlaca(String placa);

    @Query("select t from Transporte t where t.usuario = :usuario")
    Optional<Transporte> findByUsuario(@Param("usuario") Usuario usuario);
}
