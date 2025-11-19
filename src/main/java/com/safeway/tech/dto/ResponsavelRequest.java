package com.safeway.tech.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ResponsavelRequest(
        @NotBlank @Size(max = 45) String nome,
        @Size(max = 14) String cpf,
        @NotBlank String tel1,
        String tel2,
        @Email String email,
        @Valid EnderecoRequest endereco,
        List<Long> alunosIds
) {}

