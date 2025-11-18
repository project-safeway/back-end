package com.safeway.tech.models;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", nullable = false)
    private Endereco endereco;

    @Column(name = "ordem_embarque")
    private Integer ordemEmbarque;

}
