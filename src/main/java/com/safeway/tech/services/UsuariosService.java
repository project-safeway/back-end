package com.safeway.tech.services;

import com.safeway.tech.entity.Usuario;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    private final UsuarioRepository repository;

    public UsuariosService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }
}

