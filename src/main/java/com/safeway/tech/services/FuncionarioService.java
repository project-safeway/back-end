package com.safeway.tech.services;

import com.safeway.tech.dto.FuncionarioRequest;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.FuncionarioRepository;
import com.safeway.tech.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Funcionario buscarPorId(Long id){
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
    }

    public Funcionario salvarFuncionario(FuncionarioRequest request){

        if (funcionarioRepository.findByCpf(request.cpf()).isPresent()) {
            throw new RuntimeException("Funcionário com este CPF já cadastrado");
        }

        Transporte transporte = transporteRepository.findByPlaca(request.transporte().placa())
                .orElseThrow(() -> new RuntimeException("Transporte não encontrado"));

        Endereco endereco = new Endereco();
        endereco.setRua(request.endereco().rua());
        endereco.setNumero(request.endereco().numero());
        endereco.setCidade(request.endereco().cidade());
        endereco.setCep(request.endereco().cep());
        enderecoRepository.save(endereco);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(request.nome());
        funcionario.setCpf(request.cpf());
        funcionario.setTransporte(transporte);
        funcionario.setEndereco(endereco);

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarFuncionarios(){
        return funcionarioRepository.findAll();
    }

    public void excluir(Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario alterarFuncionario(FuncionarioRequest request, Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionario.setNome(request.nome());
        funcionario.setCpf(request.cpf());

        Endereco endereco = funcionario.getEndereco();
        endereco.setRua(request.endereco().rua());
        endereco.setNumero(request.endereco().numero());
        endereco.setCidade(request.endereco().cidade());
        endereco.setCep(request.endereco().cep());
        enderecoRepository.save(endereco);

        funcionario.setEndereco(endereco);

        return funcionarioRepository.save(funcionario);
    }
}
