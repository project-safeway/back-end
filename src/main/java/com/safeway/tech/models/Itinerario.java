package com.safeway.tech.models;

import com.safeway.tech.enums.TipoViagemEnum;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "itinerario")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Itinerario extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItinerario;

    @ManyToOne
    @JoinColumn(name = "fkTransporte", nullable = false)
    private Transporte transporte;

    private String nome;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    @Enumerated(EnumType.STRING)
    private TipoViagemEnum tipoViagem;

    private Boolean ativo = true;

    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItinerarioAluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "itinerario", cascade = CascadeType.ALL)
    private List<Chamada> chamadas = new ArrayList<>();
}

