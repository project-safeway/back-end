package com.safeway.tech.services;

import com.safeway.tech.models.Pagamento;
import com.safeway.tech.repository.PagamentoRepository;

import java.util.List;

public class PagamentoService {
    private PagamentoRepository repository;

    public Pagamento getById(long id){
        return repository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public Pagamento salvarPagamento(Pagamento pagamento){
        Pagamento pagamento1 = repository.save(pagamento);
        System.out.println("Pagamento Salvo!");
        return pagamento1;
    }

    public List<Pagamento> listarPagamentos(){
        return repository.findAll();
    }

    public void excluirPagamento(long id){
        repository.delete(getById(id));
    }

    //big decimal ta estranho
    public Pagamento alterarPagamento(Pagamento pagamento, long idPagamento){
        Pagamento pagamento1 = getById(pagamento.getIdPagamento());
        pagamento1.setFuncionario(pagamento.getFuncionario());
        pagamento1.setDataPagamento(pagamento.getDataPagamento());
        pagamento1.getValorPagamento(pagamento.getValorPagamento(pagamento.getValorPagamento()));
        return repository.save(pagamento1);
    }
}
