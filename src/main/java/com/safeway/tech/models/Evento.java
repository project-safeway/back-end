package com.safeway.tech.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // "id" conforme resposta da API

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 1000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate date; // ISO-8601 (YYYY-MM-DD)

    @Column(name = "type", nullable = false, length = 20)
    private String type; // manutencao, reuniao, vencimento, treinamento

    @Column(name = "priority", nullable = false, length = 20)
    private String priority; // baixa, media, alta

    @Column(name = "client_id", nullable = false)
    private Long clientId; // dono do evento
}
