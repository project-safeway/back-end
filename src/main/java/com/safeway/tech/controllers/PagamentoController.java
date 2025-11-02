package com.safeway.tech.controllers;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<Pagamento> registrarPagamento(@RequestBody @Valid PagamentoRequest request){
        Pagamento pagamento = pagamentoService.registrarPagamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    // Buscar um pagamento específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> buscarPagamento(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.buscarPagamento(id);
        return ResponseEntity.ok(pagamento);
    }

    // Buscar todos os pagamentos de um funcionário específico
    @GetMapping("/todos/{id}")
    public ResponseEntity<List<Pagamento>> buscarPagamentosPorId(@PathVariable Long id) {
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosPorId(id);
        return ResponseEntity.ok(pagamentos);
    }

    // Listar todos os pagamentos do usuário autenticado
    @GetMapping("/cobrador/{id}")
    public ResponseEntity<List<Pagamento>> listarPagamentos(@PathVariable Long id){
        List<Pagamento> pagamentos = pagamentoService.listarPagamentos(id);
        return ResponseEntity.ok(pagamentos);
    }

    // Atualizar pagamento
    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizarPagamento(@RequestBody @Valid PagamentoRequest request, @PathVariable Long id){
        Pagamento atualizado = pagamentoService.atualizarPagamento(request, id);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id){
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
