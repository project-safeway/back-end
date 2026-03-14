package com.safeway.tech.api.dto.responsavel;

import com.safeway.tech.api.dto.endereco.EnderecoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResponsavelRequest(
        @NotBlank @Size(max = 45) String nome,
        @NotBlank @Size(max = 14) String cpf,
        @NotBlank String tel1,
        String tel2,
        @Email String email,
        @Valid EnderecoRequest endereco
) {}

