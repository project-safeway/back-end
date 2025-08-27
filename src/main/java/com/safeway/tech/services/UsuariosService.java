package com.safeway.tech.services;

import com.safeway.tech.entity.Usuarios;
import com.safeway.tech.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    private final UsuariosRepository repository;

    public UsuariosService(UsuariosRepository repository) {
        this.repository = repository;
    }

    public Usuarios salvarUsuario(Usuarios usuario) {
        return repository.save(usuario);
    }
}

