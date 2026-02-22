package com.safeway.tech.models;

import com.safeway.tech.enums.TipoViagemEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "itinerario")
@Getter @Setter
@NoArgsConstructor
public class Itinerario extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_transporte", nullable = false)
    private Transporte transporte;

    private String nome;

    @Column(name = "horario_inicio")
    private Time horarioInicio;

    @Column(name = "horario_fim")
    private Time horarioFim;

    private TipoViagemEnum tipoViagem;

    @OneToMany(mappedBy = "itinerario", orphanRemoval = true)
    private List<ItinerarioAluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "itinerario", orphanRemoval = true)
    private List<ItinerarioEscola> escolas = new ArrayList<>();

    @OneToMany(mappedBy = "itinerario")
    private List<Chamada> chamadas = new ArrayList<>();
}
