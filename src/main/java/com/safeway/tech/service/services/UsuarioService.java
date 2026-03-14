package com.safeway.tech.service.services;

import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.infra.exception.UsuarioNotFoundException;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID idUsuario) {
        return usuarioRepository.getReferenceById(idUsuario);
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario alterarUsuario(Usuario novoUsuario, UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(RuntimeException::new);
        usuario.setNome(novoUsuario.getNome());
        usuario.setEmail(novoUsuario.getEmail());
        usuario.setTel1(novoUsuario.getTel1());
        usuario.setTel2(novoUsuario.getTel2());
        return usuarioRepository.save(usuario);
    }

    public void excluir(UUID idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new UsuarioNotFoundException("Usuário com ID " + idUsuario + " não encontrado.");
        }

        usuarioRepository.deleteById(idUsuario);
    }
}
