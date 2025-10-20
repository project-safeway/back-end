package com.safeway.tech.repository;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByFuncionario(Funcionario funcionario);
}
