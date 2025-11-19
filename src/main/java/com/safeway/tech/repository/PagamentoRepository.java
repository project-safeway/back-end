package com.safeway.tech.repository;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {
    List<Pagamento> findByFuncionario(Funcionario funcionario);

    @Query("select p from Pagamento p where p.funcionario.usuario.idUsuario = :userId")
    List<Pagamento> findPagamentosByUserId(@Param("userId") Long userId);
}
