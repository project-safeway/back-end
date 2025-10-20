package com.safeway.tech.controllers;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.services.PagamentoService;
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
        pagamentoService.buscarPagamento(id);
        return null;
    }

    // Buscar todos os pagamentos de um funcionário específico
    @GetMapping("/todos/{id}")
    public ResponseEntity<List<Pagamento>> buscarPagamentosPorId(@PathVariable Long id) {
        pagamentoService.buscarPagamentosPorId(id);
        return null;
    }


    // Listar todos os pagamentos de todos os funcionários
    @GetMapping
    public ResponseEntity<List<Pagamento>> listarPagamentos(){
        pagamentoService.listarPagamentos();
        return null;
    }

    // Mesma coisa do dto de funcionário
    @PutMapping
    public ResponseEntity<Pagamento> atualizarPagamento(@RequestBody PagamentoRequest request, @PathVariable Long id){
        pagamentoService.atualizarPagamento(request, id);
        return null;
    }

    // Vamos deixar ele poder fazer isso mesmo? Eu vejo como algo que ele não pode fazer
    // Se deixarmos ele deletar, será um delete lógico ou físico?
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id){
        pagamentoService.deletarPagamento(id);
        return null;
    }

}
