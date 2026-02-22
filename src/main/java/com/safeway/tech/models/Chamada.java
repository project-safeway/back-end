package com.safeway.tech.models;

import com.safeway.tech.enums.StatusChamadaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chamada")
@Getter @Setter
@NoArgsConstructor
public class Chamada extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @Enumerated(EnumType.STRING)
    private StatusChamadaEnum status;

    @OneToMany(mappedBy = "chamada")
    private List<ChamadaAluno> alunos;
}
