package com.safeway.tech.controllers;

import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.dto.ResponsavelRequest;
import com.safeway.tech.dto.ResponsavelResponse;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.services.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/responsavel")
public class ResponsavelController {
    @Autowired
    private ResponsavelService responsavelService;

    private Responsavel mapToEntity(ResponsavelRequest request) {
        EnderecoRequest er = request.endereco();
        Endereco endereco = null;
        if (er != null) {
            endereco = new Endereco();
            endereco.setLogradouro(er.logradouro());
            endereco.setNumero(er.numero());
            endereco.setCidade(er.cidade());
            endereco.setCep(er.cep());
        }
        Responsavel r = new Responsavel();
        r.setNome(request.nome());
        r.setCpf(request.cpf());
        r.setTel1(request.tel1());
        r.setTel2(request.tel2());
        r.setEmail(request.email());
        r.setEndereco(endereco);
        if (request.alunosIds() != null) {
            r.setAlunos(request.alunosIds().stream()
                    .map(id -> {
                        Aluno a = new Aluno();
                        a.setId(id);
                        return a;
                    })
                    .collect(Collectors.toList()));
        }
        return r;
    }

    private ResponsavelResponse toResponse(Responsavel r) {
        return ResponsavelResponse.fromEntity(r);
    }

    @PostMapping
    public ResponseEntity<ResponsavelResponse> salvarResponsavel(@RequestBody @Valid ResponsavelRequest request){
        Responsavel salvo = responsavelService.salvarResponsavel(mapToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(salvo));
    }

    @GetMapping
    public List<ResponsavelResponse> listarResponsaveis() {
        return responsavelService.listarResponsaveis()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{idResponsavel}")
    public ResponsavelResponse retornarUm(@PathVariable UUID idResponsavel){
        return toResponse(responsavelService.retornarUm(idResponsavel));
    }

    @DeleteMapping("/{idResponsavel}")
    public void excluir(@PathVariable UUID idResponsavel) {
        responsavelService.excluir(idResponsavel);
    }

    @PutMapping("/{idResponsavel}")
    public ResponsavelResponse alterarResponsavel(@RequestBody @Valid ResponsavelRequest novoResponsavel,@PathVariable UUID idResponsavel){
        Responsavel atualizado = responsavelService.alterarResponsavel(mapToEntity(novoResponsavel), idResponsavel);
        return toResponse(atualizado);
    }
}
