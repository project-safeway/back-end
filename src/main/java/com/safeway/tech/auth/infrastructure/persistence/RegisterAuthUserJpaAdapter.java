package com.safeway.tech.auth.infrastructure.persistence;

import com.safeway.tech.auth.core.model.RegisterAuthUserData;
import com.safeway.tech.auth.core.model.RegisteredAuthUser;
import com.safeway.tech.auth.core.port.RegisterAuthUserPort;
import com.safeway.tech.domain.enums.UserRole;
import com.safeway.tech.domain.models.Transporte;
import com.safeway.tech.domain.models.Usuario;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RegisterAuthUserJpaAdapter implements RegisterAuthUserPort {

    private final UsuarioRepository usuarioRepository;
    private final TransporteRepository transporteRepository;

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPlaca(String placa) {
        return transporteRepository.findByPlaca(placa).isPresent();
    }

    @Override
    @Transactional
    public RegisteredAuthUser create(RegisterAuthUserData data) {
        Usuario usuario = new Usuario();
        usuario.setNome(data.nome());
        usuario.setEmail(data.email());
        usuario.setPasswordHash(data.passwordHash());
        usuario.setRole(UserRole.COMMON);
        usuario.setTel1(data.telefone());
        Usuario savedUser = usuarioRepository.save(usuario);

        Transporte transporte = new Transporte();
        transporte.setPlaca(data.transportePlaca());
        transporte.setModelo(data.transporteModelo());
        transporte.setCapacidade(data.transporteCapacidade());
        transporte.setUsuario(savedUser);
        Transporte savedTransporte = transporteRepository.save(transporte);

        return new RegisteredAuthUser(savedUser.getId(), savedTransporte.getId());
    }
}

