package com.safeway.tech.dto;

public record AlunoComLocalizacao(
        Long idAluno,
        String nome,
        Long idEndereco,
        String enderecoCompleto,
        Double latitude,
        Double longitude,
        Integer ordem
) {
}