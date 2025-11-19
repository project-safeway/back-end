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
@Table(name = "despesas")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Despesa extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDespesa;

    @ManyToOne
    @JoinColumn(name = "fk_transporte", nullable = false)
    private Transporte transporte;

    private Date dataDespesa;
    private BigDecimal valorDespesa;
    private String descricao;
}
