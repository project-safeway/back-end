package com.safeway.tech.models;

import com.safeway.tech.enums.StatusChamadaEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itinerario_aluno")
@Data
public class ItinerarioAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItinerarioAluno;

    @ManyToOne
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @ManyToOne
    @JoinColumn(name = "fk_aluno", nullable = false)
    private Aluno aluno;

    private Integer ordemEmbarque;

    @Enumerated(EnumType.STRING)
    private StatusChamadaEnum statusChamada;

    @OneToMany(mappedBy = "chamada", cascade = CascadeType.ALL)
    private List<ChamadaAluno> alunos = new ArrayList<>();
}
