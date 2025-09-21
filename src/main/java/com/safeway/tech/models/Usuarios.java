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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "fkTransporte")
    private Transporte fkTransportes;

    @ManyToOne
    @JoinColumn(name = "fkAluno")
    private Responsavel fkAlunos;

    private String auth_id;

    @Column(length = 45, nullable = false)
    private String nome;

    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @Column(length = 45, nullable = false, unique = true)
    private String senha;

    @Enumerated(EnumType.STRING)
    private UserRole role; // Admin ou Common

    @Column(length = 11, nullable = false)
    @Size(min = 11, max = 11)
    private String tel1;

    @Column(length = 11)
    @Size(min = 11, max = 11)
    private String tel2;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    private LocalDateTime atualizadoEm;



    public Long getIdUsuario() {
        return idUsuario;
    }

    public Transporte getFkTransportes() {
        return fkTransportes;
    }

    public void setFkTransportes(Transporte fkTransportes) {
        this.fkTransportes = fkTransportes;
    }

    public Responsavel getFkAlunos() {
        return fkAlunos;
    }

    public void setFkAlunos(Responsavel fkAlunos) {
        this.fkAlunos = fkAlunos;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
