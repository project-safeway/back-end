package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itinerario_escola")
@Getter @Setter
@NoArgsConstructor
public class ItinerarioEscola extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_escola", nullable = false)
    private Escola escola;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(name = "ordem_parada")
    private Integer ordemParada;

    @Column(name = "ordem_global")
    private Integer ordemGlobal;
}
