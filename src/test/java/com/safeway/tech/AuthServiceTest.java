package com.safeway.tech;

import com.safeway.tech.dto.AuthResponse;
import com.safeway.tech.dto.RegisterRequest;
import com.safeway.tech.dto.TransporteRequest;
import com.safeway.tech.enums.UserRole;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import com.safeway.tech.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TransporteRepository transporteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtEncoder jwtEncoder;

    private RegisterRequest request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        request = new RegisterRequest(
                "João",
                "email@test.com",
                "12345",
                "11999999999",
                new TransporteRequest(
                        "ABC1234",
                        "Van Escolar",
                        15
                )
        );
    }

    // ------------------------------
    // TESTE: Registro OK
    // ------------------------------
    @Test
    void deveRegistrarUsuarioComSucesso() {
        when(usuarioRepository.existsByEmail("email@test.com")).thenReturn(false);

        Transporte transporte = new Transporte();
        when(transporteRepository.findByPlaca("ABC1234")).thenReturn(Optional.of(transporte));

        when(passwordEncoder.encode("12345")).thenReturn("HASH123");

        Usuario salvo = new Usuario();
        salvo.setIdUsuario(1L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(salvo);

        assertDoesNotThrow(() -> authService.register(request));

        verify(usuarioRepository).save(any(Usuario.class));
        verify(transporteRepository).findByPlaca("ABC1234");
    }

    // ------------------------------
    // TESTE: Email já cadastrado
    // ------------------------------
    @Test
    void deveFalharAoRegistrarEmailJaExistente() {
        when(usuarioRepository.existsByEmail("email@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> authService.register(request));
    }

    // ------------------------------
    // TESTE: Autenticação OK
    // ------------------------------
    @Test
    void deveAutenticarComSucesso() {
        Usuario user = new Usuario();
        user.setIdUsuario(99L);
        user.setRole(UserRole.ADMIN);

        when(usuarioRepository.findByEmail("email@test.com")).thenReturn(Optional.of(user));
        when(user.isLoginCorrect("12345", passwordEncoder)).thenReturn(true);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getTokenValue()).thenReturn("TOKEN_123");

        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(jwt);

        AuthResponse response = authService.autenticar("email@test.com", "12345");

        assertNotNull(response);
        assertEquals("TOKEN_123", response.getAccessToken());
    }

    // ------------------------------
    // TESTE: Usuário não existe
    // ------------------------------
    @Test
    void deveFalharAutenticacaoUsuarioNaoExiste() {
        when(usuarioRepository.findByEmail("email@test.com")).thenReturn(Optional.empty());

        assertThrows(BadCredentialsException.class,
                () -> authService.autenticar("email@test.com", "12345"));
    }

    // ------------------------------
    // TESTE: Senha incorreta
    // ------------------------------
    @Test
    void deveFalharSenhaIncorreta() {
        Usuario user = new Usuario();
        when(usuarioRepository.findByEmail("email@test.com")).thenReturn(Optional.of(user));
        when(user.isLoginCorrect("senhaErrada", passwordEncoder)).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.autenticar("email@test.com", "senhaErrada"));
    }
}
