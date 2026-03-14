package com.safeway.tech.api.controllers;

import com.safeway.tech.api.dto.aluno.AlunoFeignResponse;
import com.safeway.tech.api.dto.aluno.AlunoResponse;
import com.safeway.tech.api.dto.aluno.AlunoUpdateRequest;
import com.safeway.tech.api.dto.aluno.CadastroAlunoCompletoRequest;
import com.safeway.tech.api.dto.endereco.EnderecoResponse;
import com.safeway.tech.domain.models.Aluno;
import com.safeway.tech.domain.models.Endereco;
import com.safeway.tech.service.mappers.EnderecoMapper;
import com.safeway.tech.service.services.AlunoService;
import com.safeway.tech.service.services.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;
    private final EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<UUID> cadastrarAlunoCompleto(
            @RequestBody @Valid CadastroAlunoCompletoRequest request
    ) {
        UUID idAluno = alunoService.cadastrarAlunoCompleto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(idAluno);
    }

    @GetMapping("/{alunoId}/enderecos")
    public ResponseEntity<List<EnderecoResponse>> listarEnderecosDoAluno(
            @PathVariable UUID alunoId
    ) {
        List<Endereco> enderecos = enderecoService.listarEnderecosDisponiveis(alunoId);
        List<EnderecoResponse> response = enderecos.stream().map(EnderecoMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> listarDadosAluno(@PathVariable UUID alunoId) {
        AlunoResponse alunoResponse = alunoService.obterDadosAluno(alunoId);
        return ResponseEntity.ok(alunoResponse);
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @PathVariable UUID alunoId,
            @RequestBody @Valid AlunoUpdateRequest request
    ) {
        AlunoResponse atualizado = alunoService.atualizarAluno(alunoId, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{alunoId}")
    public ResponseEntity<Void> deletarAluno(@PathVariable UUID alunoId) {
        alunoService.deletarAluno(alunoId);
        return ResponseEntity.noContent().build();
    }

    /*

        MÉTODOS USADOS NO FEIGN CLIENT

     */

    @GetMapping("/feign/{alunoId}")
    public ResponseEntity<AlunoFeignResponse> buscarAlunoPorId(@PathVariable UUID alunoId) {
        Aluno aluno = alunoService.buscarAlunoPorId(alunoId);
        return ResponseEntity.ok(AlunoFeignResponse.fromEntity(aluno));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<AlunoFeignResponse>> buscarTodosAtivos() {
        return ResponseEntity.ok(alunoService.buscarTodosAtivos());
    }

    @PostMapping("/lote")
    public ResponseEntity<List<AlunoFeignResponse>> buscarPorIdEmLote(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(alunoService.buscarPorIdEmLote(ids));
    }
}
