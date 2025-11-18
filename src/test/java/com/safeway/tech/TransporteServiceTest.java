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
import com.safeway.tech.services.TransporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransporteServiceTest {

    @InjectMocks
    private TransporteService service;

    @Mock
    private TransporteRepository repository;

    private Transporte transporte;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        transporte = new Transporte();
        transporte.setIdTransporte(1L);
        transporte.setPlaca("ABC1234");
        transporte.setModelo("Van");
        transporte.setCapacidade(15);
    }

    // ------------------------------------------------------------
    @Test
    void deveBuscarTransportePorId() {
        when(repository.findById(1L)).thenReturn(Optional.of(transporte));

        Transporte encontrado = service.getById(1L);

        assertEquals(1L, encontrado.getIdTransporte());
        assertEquals("ABC1234", encontrado.getPlaca());
        verify(repository).findById(1L);
    }

    // ------------------------------------------------------------
    @Test
    void deveSalvarTransporte() {
        when(repository.save(transporte)).thenReturn(transporte);

        Transporte salvo = service.salvarTransporte(transporte);

        assertNotNull(salvo);
        assertEquals("ABC1234", salvo.getPlaca());
        verify(repository).save(transporte);
    }

    // ------------------------------------------------------------
    @Test
    void deveListarTransportes() {
        when(repository.findAll()).thenReturn(List.of(transporte));

        List<Transporte> lista = service.listarTransportes();

        assertEquals(1, lista.size());
        verify(repository).findAll();
    }

    // ------------------------------------------------------------
    @Test
    void deveExcluirTransporte() {
        when(repository.findById(1L)).thenReturn(Optional.of(transporte));

        service.excluirTransporte(1L);

        verify(repository).delete(transporte);
    }

    // ------------------------------------------------------------
    @Test
    void deveAlterarTransporte() {
        Transporte novo = new Transporte();
        novo.setIdTransporte(1L);
        novo.setPlaca("XYZ9876");
        novo.setModelo("Ônibus");
        novo.setCapacidade(40);
        novo.setFuncionarios(null);
        novo.setAlunosTransportes(null);
        novo.setDespesas(null);

        when(repository.findById(1L)).thenReturn(Optional.of(transporte));
        when(repository.save(any(Transporte.class))).thenReturn(novo);

        Transporte atualizado = service.alterarTransporte(novo, 1);

        assertEquals("XYZ9876", atualizado.getPlaca());
        assertEquals("Ônibus", atualizado.getModelo());
        assertEquals(40, atualizado.getCapacidade());
        verify(repository).findById(1L);
        verify(repository).save(any(Transporte.class));
    }
}
