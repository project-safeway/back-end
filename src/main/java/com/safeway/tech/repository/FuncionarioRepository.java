package com.safeway.tech.repository;

import com.safeway.tech.models.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByCpf(String cpf);

    Optional<Funcionario> findByCpfAndUsuario_IdUsuario(String cpf, Long userId);

    List<Funcionario> findAllByUsuario_IdUsuario(Long userId);
}
