package com.safeway.tech.domain.models;

import com.safeway.tech.domain.enums.EventoTypeEnum;
import com.safeway.tech.domain.enums.PriorityEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "eventos")
@Getter @Setter
@NoArgsConstructor
public class Evento extends BaseEntity {

    // Dono do evento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date; // ISO-8601 (YYYY-MM-DD)

    @Column(name = "type", nullable = false, length = 20)
    private EventoTypeEnum type;

    @Column(name = "priority", nullable = false, length = 20)
    private PriorityEnum priority = PriorityEnum.MEDIA;
}
