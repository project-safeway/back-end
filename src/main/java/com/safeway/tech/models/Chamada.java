package com.safeway.tech.models;

import com.safeway.tech.enums.StatusChamadaEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "chamada")
@Data
@EqualsAndHashCode
public class Chamada extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_itinerario", nullable = false)
    private Itinerario itinerario;

    @Enumerated(EnumType.STRING)
    private StatusChamadaEnum status;
}
