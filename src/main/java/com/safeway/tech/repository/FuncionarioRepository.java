package com.safeway.tech.repository;

import com.safeway.tech.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    @Query("SELECT f FROM Funcionario f WHERE f.cpf = :cpf AND f.usuario.id = :userId")
    Optional<Funcionario> findByCpfAndUsuario_IdUsuario(@Param("cpf") String cpf, @Param("userId") UUID userId);

    @Query("SELECT f FROM Funcionario f WHERE f.usuario.id = :userId")
    List<Funcionario> findAllByUsuario_IdUsuario(@Param("userId") UUID userId);
}
