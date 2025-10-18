package com.safeway.tech.repository;

import com.safeway.tech.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento,Long> {
}
