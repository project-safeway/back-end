package com.safeway.tech.services;

import com.safeway.tech.dto.PagamentoRequest;
import com.safeway.tech.dto.PagamentoResponse;
import com.safeway.tech.models.Pagamento;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.PagamentoRepository;
import com.safeway.tech.specification.PagamentoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UsuarioService usuarioService;

    public PagamentoResponse registrarPagamento(PagamentoRequest request){
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioService.retornarUm(userId);

        Pagamento pagamento = new Pagamento();
        pagamento.setValorPagamento(request.valorPagamento());
        pagamento.setDataPagamento(request.dataPagamento());
        pagamento.setDescricao(request.descricao());
        pagamento.setUsuario(usuario);

        Pagamento pagamentoDB = pagamentoRepository.save(pagamento);

        return PagamentoResponse.fromEntity(pagamentoDB);
    }

    public Page<PagamentoResponse> buscarPagamentos(String descricao, LocalDate dataInicio, LocalDate dataFim, Double valorMinimo, Double valorMaximo, Pageable pageable) {
        Long userId = currentUserService.getCurrentUserId();

        Specification<Pagamento> spec = Specification.allOf(
                PagamentoSpecs.comDescricao(descricao),
                PagamentoSpecs.comPeriodo(dataInicio, dataFim),
                PagamentoSpecs.comValor(valorMinimo, valorMaximo),
                PagamentoSpecs.comUsuario(userId)
        );

        Page<Pagamento> result = pagamentoRepository.findAll(spec, pageable);

        return result.map(PagamentoResponse::fromEntity);
    }

    public PagamentoResponse atualizarPagamento(Long idPagamento, PagamentoRequest request) {
        Pagamento pagamento = pagamentoRepository.findById(idPagamento)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID: " + idPagamento));
        pagamento.setDataPagamento(request.dataPagamento());
        pagamento.setValorPagamento(request.valorPagamento());

        Pagamento pagamentoDB = pagamentoRepository.save(pagamento);

        return PagamentoResponse.fromEntity(pagamentoDB);
    }

    public void deletarPagamento(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID: " + id));

        Long userId = currentUserService.getCurrentUserId();
        if (!pagamento.getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Acesso negado para deletar este pagamento.");
        }
        pagamentoRepository.delete(pagamento);
    }
}
