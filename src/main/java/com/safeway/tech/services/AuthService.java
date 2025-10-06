package com.safeway.tech.services;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.enums.UserRole;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    public void register(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.senha()));
        usuario.setRole(UserRole.ADMIN); // Tem que estudar essa regra aqui
        usuario.setTel1(request.telefone());

        Transporte transporte = new Transporte();
        transporte.setPlaca(request.transporte().placa());
        transporte.setModelo(request.transporte().modelo());
        transporte.setCapacidade(request.transporte().capacidade());

        Transporte transporteSalvo = transporteRepository.save(transporte);

        usuario.setTransporte(transporteSalvo);

        usuarioRepository.save(usuario);
    }

    public AuthResponse autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isEmpty() || !usuario.get().isLoginCorrect(senha, passwordEncoder)) {
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        Instant now = Instant.now();
        Long expiresIn = 60 * 60 * 23L;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("safeway-tech")
                .subject(usuario.get().getIdUsuario().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("role", usuario.get().getRole().toString())
                .build();

        String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new AuthResponse(jwtValue, expiresIn);
    }
}