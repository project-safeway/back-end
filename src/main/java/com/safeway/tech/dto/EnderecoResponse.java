package com.safeway.tech.dto;

import com.safeway.tech.models.Endereco;

import java.util.UUID;

public record EnderecoResponse(
        UUID id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String cep,
        Double latitude,
        Double longitude,
        String tipo,
        Boolean ativo,
        Boolean principal
) {
    public static EnderecoResponse fromEntity(Endereco endereco) {
        return new EnderecoResponse(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getUf(),
                endereco.getCep(),
                endereco.getLatitude(),
                endereco.getLongitude(),
                endereco.getTipo(),
                endereco.getAtivo(),
                endereco.getPrincipal()
        );
    }
}
