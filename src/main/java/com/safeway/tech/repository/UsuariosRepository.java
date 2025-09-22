package com.safeway.tech.repository;

import com.safeway.tech.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
