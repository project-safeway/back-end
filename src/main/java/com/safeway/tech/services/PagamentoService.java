package com.safeway.tech.services;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private UsuarioService usuarioService;

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

    // Aqui vai ser melhor mudar para paginação
    public List<Pagamento> listarPagamentos(Long id) {
        Usuario usuario = usuarioService.retornarUm(id);
        List<Pagamento> pagamentos = pagamentoRepository.findPagamentosByUsuario(usuario);
        return pagamentos;
    }

    public Pagamento atualizarPagamento(PagamentoRequest request, Long id) {
        Pagamento pagamento = buscarPagamento(id);

        pagamento.setDataPagamento(request.dataPagamento());
        pagamento.setValorPagamento(request.valorPagamento());

        Funcionario funcionario = funcionarioService.buscarPorId(request.idFuncionario());

        pagamento.setFuncionario(funcionario);
        pagamento = pagamentoRepository.save(pagamento);
        return pagamento;
    }

    public void deletarPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
