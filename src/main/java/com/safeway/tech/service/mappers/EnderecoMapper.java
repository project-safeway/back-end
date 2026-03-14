package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.models.Endereco;

public class EnderecoMapper {

    public static EnderecoResponse toResponse(Endereco entity) {
        return new EnderecoResponse(
                entity.getId(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getUf(),
                entity.getCep(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getTipo(),
                entity.getAtivo(),
                entity.getPrincipal()
        );
    }

}
