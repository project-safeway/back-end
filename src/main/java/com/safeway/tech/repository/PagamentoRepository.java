package com.safeway.tech.repository;

import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByFuncionario(Funcionario funcionario);

    @Query("select p from Pagamento p where :usuario member of p.funcionario.transporte.usuarios")
    List<Pagamento> findPagamentosByUsuario(Usuario usuario);
}
