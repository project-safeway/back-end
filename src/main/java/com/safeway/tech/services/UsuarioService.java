package com.safeway.tech.services;

import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario retornarUm(Long idUsuario){
        return repository.findById(idUsuario).orElseThrow(RuntimeException::new);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public void excluir(Long idUsuario) {
        repository.deleteById(idUsuario);
    }

    public Usuario alterarUsuario(Usuario novoUsuario, Long idUsuario){
        Usuario usuario = repository.findById(idUsuario).orElseThrow(RuntimeException::new);
        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setTel1(novoUsuario.getTel1());
        usuario.setTel2(novoUsuario.getTel2());
        return repository.save(usuario);
    }
}
