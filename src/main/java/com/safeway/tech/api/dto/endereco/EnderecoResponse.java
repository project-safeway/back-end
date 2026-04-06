package com.safeway.tech.api.dto.endereco;

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
}
