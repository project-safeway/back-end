package com.safeway.tech.services;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.dto.PagamentoResponse;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.repository.PagamentoRepository;
import com.safeway.tech.specification.PagamentoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private CurrentUserService currentUserService;

    public PagamentoResponse registrarPagamento(Long idFuncionario, PagamentoRequest request){
        Funcionario funcionario = funcionarioService.buscarPorId(idFuncionario);

        Pagamento pagamento = new Pagamento();
        pagamento.setFuncionario(funcionario);
        pagamento.setValorPagamento(request.valorPagamento());
        pagamento.setDataPagamento(request.dataPagamento());

        Pagamento pagamentoDB = pagamentoRepository.save(pagamento);

        return PagamentoResponse.fromEntity(pagamentoDB);
    }

    public Page<PagamentoResponse> buscarPagamentos(Long funcionarioId, LocalDate dataInicio, LocalDate dataFim, Double valorMinimo, Double valorMaximo, Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();

        Specification<Pagamento> spec = Specification.allOf(
                PagamentoSpecs.comFuncionario(funcionarioId),
                PagamentoSpecs.comPeriodo(dataInicio, dataFim),
                PagamentoSpecs.comValor(valorMinimo, valorMaximo),
                PagamentoSpecs.comUsuario(userId)
        );

        Page<Pagamento> result = pagamentoRepository.findAll(spec, pageable);

        return result.map(PagamentoResponse::fromEntity);
    }

    public PagamentoResponse atualizarPagamento(Long idPagamento, Long idFuncionario, PagamentoRequest request) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID: " + idPagamento));
        pagamento.setDataPagamento(request.dataPagamento());
        pagamento.setValorPagamento(request.valorPagamento());

        if (idFuncionario != null) {
            Funcionario funcionario = funcionarioService.buscarPorId(idFuncionario);
            pagamento.setFuncionario(funcionario);
        }

        Pagamento pagamentoDB = pagamentoRepository.save(pagamento);

        return PagamentoResponse.fromEntity(pagamentoDB);
    }

    public void deletarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID: " + id));

        Long userId = currentUserService.getCurrentUserId();
        if (!pagamento.getFuncionario().getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Acesso negado para deletar este pagamento.");
        }
        pagamentoRepository.delete(pagamento);
    }
}
