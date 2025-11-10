package com.safeway.tech.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "itinerario_aluno")
@Data
@EqualsAndHashCode
public class ItinerarioAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @ManyToOne
    @JoinColumn(name = "fk_aluno", nullable = false)
    private Aluno aluno;

    @Column(name = "ordem_embarque")
    private Integer ordemEmbarque;

}
