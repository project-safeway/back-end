package com.safeway.tech.services;

import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.enums.UserRole;
import com.safeway.tech.repository.UsuariosRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuariosRepository repository;

    public AuthService(UsuariosRepository repository) {
        this.repository = repository;
    }

    public void register(RegisterRequest request) {
        if (request.getNome() == null || request.getEmail() == null || request.getSenha() == null) {
            throw new RuntimeException("Todos os campos são obrigatórios");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(request.getSenha()); // Precisa alterar para hash aqui dps
        usuario.setRole(UserRole.COMMON);
        usuario.setTel1("00000000000");

        repository.save(usuario);
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || senha == null) {
            throw new RuntimeException("Email e senha são obrigatórios");
        }

        return repository.findByEmail(email)
                .map(usuario -> usuario.getPasswordHash().equals(senha)) // Precisa alterar para hash aqui dps
                .orElse(false);
    }
}