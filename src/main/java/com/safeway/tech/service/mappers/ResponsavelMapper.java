package com.safeway.tech.service.mappers;

import com.safeway.tech.api.dto.responsavel.ResponsavelResponse;
import com.safeway.tech.domain.models.Responsavel;

public class ResponsavelMapper {

    public static ResponsavelResponse toResponse(Responsavel responsavel) {
        return new ResponsavelResponse(
                responsavel.getId(),
                responsavel.getNome(),
                responsavel.getCpf(),
                responsavel.getTel1(),
                responsavel.getTel2(),
                responsavel.getEmail(),
                EnderecoMapper.toResponse(responsavel.getEndereco())
        );
    }

}
