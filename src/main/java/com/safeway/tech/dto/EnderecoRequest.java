package com.safeway.tech.dto;

public record EnderecoRequest(
        String rua,
        String cep,
        String numero,
        String cidade
) {
}
