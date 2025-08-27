package com.safeway.tech.repository;

import com.safeway.tech.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Optional<Usuarios> findByEmail(String email);
}
