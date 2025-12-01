package com.safeway.tech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "pagamentos")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
public class Pagamento extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPagamento;

    private LocalDate dataPagamento;
    private Double valorPagamento;
    private String descricao;

    @ManyToOne(optional = false)
    private Usuario usuario;
}
