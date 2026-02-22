package com.safeway.tech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "despesas")
@Getter @Setter
@NoArgsConstructor
public class Despesa extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "fk_transporte", nullable = false)
    private Transporte transporte;

    private Date dataDespesa;
    private BigDecimal valorDespesa;
    private String descricao;
}
