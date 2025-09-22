package com.safeway.tech.services;

import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.enums.UserRole;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (request.getNome() == null || request.getEmail() == null || request.getSenha() == null) {
            throw new RuntimeException("Todos os campos são obrigatórios");
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getSenha()));
        usuario.setRole(UserRole.COMMON);
        usuario.setTel1("00000000000");

        usuarioRepository.save(usuario);
    }

    public boolean autenticar(String email, String senha) {
        if (email == null || senha == null) {
            throw new RuntimeException("Email e senha são obrigatórios");
        }
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) return false;

        return passwordEncoder.matches(senha, usuario.getPasswordHash());
    }
}