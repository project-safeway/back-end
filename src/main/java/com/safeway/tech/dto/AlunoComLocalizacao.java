package com.safeway.tech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlunoComLocalizacao(
        @JsonProperty("alunoId") Long idAluno,
        @JsonProperty("nomeAluno") String nome,
        @JsonProperty("enderecoId") Long idEndereco,
        String enderecoCompleto,
        Double latitude,
        Double longitude,
        @JsonProperty("ordemEmbarque") Integer ordem
) {
}