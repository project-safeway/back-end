package com.safeway.tech.services;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    private FuncionarioService funcionarioService;

    public Pagamento registrarPagamento(PagamentoRequest request){

        Funcionario funcionario = funcionarioService.buscarPorId(request.idFuncionario());

        Pagamento pagamento = new Pagamento();

        pagamento.setFuncionario(funcionario);
        pagamento.setValorPagamento(request.valorPagamento());
        pagamento.setDataPagamento(request.dataPagamento()); // Pode ser que aqui tenha problema de timezone

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pagamento não encontrado")
        );

        return pagamento;
    }

    public List<Pagamento> buscarPagamentosPorId(Long id) {
        Funcionario funcionario = funcionarioService.buscarPorId(id);
        List<Pagamento> pagamentos = pagamentoRepository.findByFuncionario(funcionario);
        return pagamentos;
    }

    public List<Pagamento> listarPagamentos() {

    }

    public Pagamento atualizarPagamento(PagamentoRequest request, Long id) {
    }

    public void deletarPagamento(Long id) {
    }
}
