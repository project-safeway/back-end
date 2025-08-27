package com.safeway.tech.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Endereco {
    @Id
    private Long id;
    public String rua;
    public String bairro;
    public String cidade;
    public String estado;
    public String CEP;
    public String numero;
}
