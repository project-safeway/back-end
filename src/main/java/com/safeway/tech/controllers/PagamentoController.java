package com.safeway.tech.controllers;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.dto.PagamentoResponse;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento(@RequestParam Long idFuncionario, @RequestBody @Valid PagamentoRequest request){
        Pagamento pagamento = pagamentoService.registrarPagamento(idFuncionario, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> buscarPagamento(
            @RequestParam(required = false) Long funcionarioId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Double valorMinimo,
            @RequestParam(required = false) Double valorMaximo,
            @PageableDefault(page = 0, size = 10, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable){
        Page<PagamentoResponse> pagamento = pagamentoService
                .buscarPagamentos(funcionarioId, dataInicio, dataFim, valorMinimo, valorMaximo, pageable);
        return ResponseEntity.ok(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@PathVariable Long id,
                                                        @RequestParam Long idFuncionario,
                                                        @RequestBody @Valid PagamentoRequest request){
        Pagamento atualizado = pagamentoService.atualizarPagamento(id, idFuncionario, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id){
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
