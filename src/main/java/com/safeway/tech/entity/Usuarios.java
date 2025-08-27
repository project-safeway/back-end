package com.safeway.tech.entity;

import com.safeway.tech.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private Integer fkTransportes;
    private Integer fkAlunos;
    private String auth_id;
    private String nome;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role; // Admin ou Common

    private String tel1;
    private String tel2;
}
