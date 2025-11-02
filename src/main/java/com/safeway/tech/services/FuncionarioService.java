package com.safeway.tech.services;

import com.safeway.tech.dto.FuncionarioRequest;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Funcionario;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.FuncionarioRepository;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CurrentUserService currentUserService;

    public Funcionario buscarPorId(Long id){
        Long userId = currentUserService.getCurrentUserId();
        Funcionario f = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        if (!f.getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Sem permissão para acessar este funcionário");
        }
        return f;
    }

    public Funcionario salvarFuncionario(FuncionarioRequest request){
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);

        if (request.cpf() != null && funcionarioRepository.findByCpfAndUsuario_IdUsuario(request.cpf(), userId).isPresent()) {
            throw new RuntimeException("Funcionário com este CPF já cadastrado para este usuário");
        }

        Transporte transporte = transporteRepository.findByPlacaAndUsuario_IdUsuario(request.transporte().placa(), userId)
                .orElseThrow(() -> new RuntimeException("Transporte não encontrado para este usuário"));

        Endereco endereco = new Endereco();
        endereco.setLogradouro(request.endereco().rua());
        endereco.setNumero(request.endereco().numero());
        endereco.setCidade(request.endereco().cidade());
        endereco.setCep(request.endereco().cep());
        enderecoRepository.save(endereco);

        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(usuario);
        funcionario.setNome(request.nome());
        funcionario.setCpf(request.cpf());
        funcionario.setTransporte(transporte);
        funcionario.setEndereco(endereco);

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarFuncionarios(){
        Long userId = currentUserService.getCurrentUserId();
        return funcionarioRepository.findAllByUsuario_IdUsuario(userId);
    }

    public void excluir(Long id){
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario alterarFuncionario(FuncionarioRequest request, Long id){
        Long userId = currentUserService.getCurrentUserId();
        Funcionario funcionario = buscarPorId(id);

        funcionario.setNome(request.nome());
        funcionario.setCpf(request.cpf());

        Endereco endereco = funcionario.getEndereco();
        endereco.setLogradouro(request.endereco().rua());
        endereco.setNumero(request.endereco().numero());
        endereco.setCidade(request.endereco().cidade());
        endereco.setCep(request.endereco().cep());
        enderecoRepository.save(endereco);

        funcionario.setEndereco(endereco);

        // opcional: validar/atualizar transporte do mesmo usuário
        if (request.transporte() != null && request.transporte().placa() != null) {
            Transporte transporte = transporteRepository.findByPlacaAndUsuario_IdUsuario(request.transporte().placa(), userId)
                    .orElseThrow(() -> new RuntimeException("Transporte não encontrado para este usuário"));
            funcionario.setTransporte(transporte);
        }

        return funcionarioRepository.save(funcionario);
    }
}
