package com.safeway.tech.controllers;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.dto.PagamentoResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoResponse> registrarPagamento(@RequestBody @Valid PagamentoRequest request){
        PagamentoResponse pagamento = pagamentoService.registrarPagamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping
    public ResponseEntity<Page<PagamentoResponse>> buscarPagamento(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) Double valorMinimo,
            @RequestParam(required = false) Double valorMaximo,
            @PageableDefault(page = 0, size = 10, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable){
        Page<PagamentoResponse> pagamento = pagamentoService
                .buscarPagamentos(descricao, dataInicio, dataFim, valorMinimo, valorMaximo, pageable);
        return ResponseEntity.ok(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponse> atualizarPagamento(@PathVariable Long id,
                                                        @RequestBody @Valid PagamentoRequest request){
        PagamentoResponse atualizado = pagamentoService.atualizarPagamento(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id){
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
