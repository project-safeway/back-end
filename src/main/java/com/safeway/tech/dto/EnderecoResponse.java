package com.safeway.tech.dto;

import com.safeway.tech.models.Endereco;

public record EnderecoResponse(
        Long id,
        Long alunoId,
        String alunoNome,
        Long responsavelId,
        String responsavelNome,
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
                endereco.getIdEndereco(),
                endereco.getAluno().getIdAluno(),
                endereco.getAluno().getNome(),
                endereco.getResponsavel() != null ? endereco.getResponsavel().getIdResponsavel() : null,
                endereco.getResponsavel() != null ? endereco.getResponsavel().getNome() : null,
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
