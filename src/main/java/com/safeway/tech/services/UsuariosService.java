package com.safeway.tech.services;

import com.safeway.tech.models.Usuarios;
import com.safeway.tech.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {
    @Autowired
    private UsuariosRepository repository;

    public List<Usuarios> listarUsuarios() {
        return repository.findAll();
    }

    public Usuarios retornarUm(int indice){
        Usuarios usuario = repository.findById(indice).orElseThrow(RuntimeException::new);
        return usuario;
    }

    public Usuarios salvarUsuario(Usuarios usuario) {
        return repository.save(usuario);
    }

    public void excluir(Integer idUsuario) {
        repository.deleteById(idUsuario);
    }

    public Usuarios alterarUsuario(Usuarios novoUsuario,int indice){
        Usuarios usuario = repository.findById(indice).orElseThrow(RuntimeException::new);
        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setTel1(novoUsuario.getTel1());
        usuario.setTel2(novoUsuario.getTel2());
        return repository.save(usuario);
    }
}

