package com.safeway.tech.models;

import com.safeway.tech.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Usuario extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idUsuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fkTransporte", nullable = false)
    private Transporte transporte;

    @ManyToOne
    @JoinColumn(name = "fkResponsavel")
    private Responsavel responsavel;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole role;

    @Column(nullable = false, length = 15)
    private String tel1;

    @Column(length = 15)
    private String tel2;

    public boolean isLoginCorrect(String senha, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(senha, this.passwordHash);
    }
}
