package com.safeway.tech.service.services;

import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario retornarUm(UUID idUsuario) {
        return repository.findById(idUsuario).orElseThrow(RuntimeException::new);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public void excluir(UUID idUsuario) {
        repository.deleteById(idUsuario);
    }

    public Usuario alterarUsuario(Usuario novoUsuario, UUID idUsuario) {
        Usuario usuario = repository.findById(idUsuario).orElseThrow(RuntimeException::new);
        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setTel1(novoUsuario.getTel1());
        usuario.setTel2(novoUsuario.getTel2());
        return repository.save(usuario);
    }
}
