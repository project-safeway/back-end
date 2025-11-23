package com.safeway.tech.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "itinerario_escola")
@Data
@EqualsAndHashCode(callSuper = true)
public class ItinerarioEscola extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
