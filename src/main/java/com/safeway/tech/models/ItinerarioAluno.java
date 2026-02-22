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
@Table(name = "itinerario_aluno")
@Getter @Setter
@NoArgsConstructor
public class ItinerarioAluno extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @ManyToOne
    @JoinColumn(name = "fk_aluno", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(name = "ordem_embarque")
    private Integer ordemEmbarque;

    @Column(name = "ordem_global")
    private Integer ordemGlobal;

}
