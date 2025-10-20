package com.safeway.tech.services;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.enums.UserRole;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    public void register(RegisterRequest request) {
        // Campos obrigatórios já validados pelo @Valid no controller
        log.info("Tentativa de registro para email: {}", request.email());

        if (usuarioRepository.existsByEmail(request.email())) {
            log.warn("Email já cadastrado: {}", request.email());
            throw new RuntimeException("Email já cadastrado");
        }

        Transporte transporteEncontrado = null;
        if (request.transporte().placa() != null && !request.transporte().placa().isBlank()) {
            String placaNormalizada = request.transporte().placa().trim().toUpperCase();
            transporteEncontrado = transporteRepository.findByPlaca(placaNormalizada).orElse(null);
            if (transporteEncontrado == null) {
                log.warn("Placa informada, porém transporte não encontrado: {}", request.transporte().placa());
            }
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.senha()));
        usuario.setRole(UserRole.COMMON);
        usuario.setTel1(request.telefone());
        usuario.setTransporte(transporteEncontrado); // pode ser null

        usuarioRepository.save(usuario);
        log.info("Usuário registrado com sucesso: {}", request.email());
    }

    public AuthResponse autenticar(String email, String senha) {
        log.info("Tentativa de login para email: {}", email);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isEmpty()) {
            log.warn("Usuário não encontrado: {}", email);
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        if (!usuario.get().isLoginCorrect(senha, passwordEncoder)) {
            log.warn("Senha incorreta para: {}", email);
            throw new BadCredentialsException("Email ou senha inválidos");
        }

        log.info("Login bem-sucedido para: {}", email);

        Instant now = Instant.now();
        long expiresIn = 60 * 60 * 23L; // em segundos

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