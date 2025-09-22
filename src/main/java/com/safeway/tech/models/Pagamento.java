package com.safeway.tech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;

    @ManyToOne
    @JoinColumn(name = "fkFuncionario", nullable = false)
    private Funcionario funcionario;

    private Date dataPagamento;
    private BigDecimal valorPagamento;
}
