package com.safeway.tech.controllers;

import com.safeway.tech.dto.ResponsavelRequest;
import com.safeway.tech.dto.EnderecoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.services.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
            endereco.setLogradouro(er.rua());
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
                        a.setIdAluno(id);
                        return a;
                    })
                    .collect(Collectors.toList()));
        }
        return r;
    }

    @PostMapping
    public ResponseEntity<Responsavel> salvarResponsavel(@RequestBody @Valid ResponsavelRequest request){
        Responsavel salvo = responsavelService.salvarResponsavel(mapToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<Responsavel> listarResponsaveis() {
        return responsavelService.listarResponsaveis();
    }

    @GetMapping("/{idResponsavel}")
    public Responsavel retornarUm(@PathVariable Long idResponsavel){
        return responsavelService.retornarUm(idResponsavel);
    }

    @DeleteMapping("/{idResponsavel}")
    public void excluir(@PathVariable Long idResponsavel) {
        responsavelService.excluir(idResponsavel);
    }

    @PutMapping("/{idResponsavel}")
    public Responsavel alterarResponsavel(@RequestBody @Valid ResponsavelRequest novoResponsavel,@PathVariable Long idResponsavel){
        return responsavelService.alterarResponsavel(mapToEntity(novoResponsavel), idResponsavel);
    }
}
