package com.safeway.tech.services;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private CurrentUserService currentUserService;

    public Pagamento registrarPagamento(PagamentoRequest request){
        Funcionario funcionario = funcionarioService.buscarPorId(request.idFuncionario());

        Pagamento pagamento = new Pagamento();
        pagamento.setFuncionario(funcionario);
        pagamento.setValorPagamento(request.valorPagamento());
        pagamento.setDataPagamento(request.dataPagamento());

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento buscarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Pagamento não encontrado")
        );
        // garante escopo
        Long userId = currentUserService.getCurrentUserId();
        if (!pagamento.getFuncionario().getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Sem permissão para acessar este pagamento");
        }
        return pagamento;
    }

    public List<Pagamento> buscarPagamentosPorId(Long funcionarioId) {
        // valida escopo via buscarPorId
        Funcionario funcionario = funcionarioService.buscarPorId(funcionarioId);
        return pagamentoRepository.findByFuncionario(funcionario);
    }

    public List<Pagamento> listarPagamentos(Long ignoredUserId) {
        Long userId = currentUserService.getCurrentUserId();
        return pagamentoRepository.findPagamentosByUserId(userId);
    }

    public Pagamento atualizarPagamento(PagamentoRequest request, Long id) {
        Pagamento pagamento = buscarPagamento(id);
        pagamento.setDataPagamento(request.dataPagamento());
        pagamento.setValorPagamento(request.valorPagamento());

        // revalida e atualiza funcionário se informado
        if (request.idFuncionario() != null) {
            Funcionario funcionario = funcionarioService.buscarPorId(request.idFuncionario());
            pagamento.setFuncionario(funcionario);
        }

        return pagamentoRepository.save(pagamento);
    }

    public void deletarPagamento(Long id) {
        Pagamento pagamento = buscarPagamento(id);
        pagamentoRepository.delete(pagamento);
    }
}
