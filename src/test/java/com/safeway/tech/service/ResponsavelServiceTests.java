package com.safeway.tech.service;

import com.safeway.tech.models.Endereco;
import com.safeway.tech.repository.ResponsavelRepository;
import com.safeway.tech.services.ResponsavelService;
import com.safeway.tech.models.Responsavel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ResponsavelServiceTests {
    @InjectMocks
    private ResponsavelService service;

    @Mock
    private ResponsavelRepository repository;

    private Responsavel responsavel;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(10L);
        endereco.setLogradouro("Rua A");
        endereco.setNumero("123");
        endereco.setComplemento("Casa");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setCep("01001-000");

        responsavel = new Responsavel();
        responsavel.setIdResponsavel(1L);
        responsavel.setNome("João");
        responsavel.setEndereco(endereco);
    }

    @Test
    void deveAlterarResponsavel() {
        Endereco novoEnd = new Endereco(
                20L,
                "Rua Nova",
                "999",
                "Apto 10",
                "Bairro Novo",
                "Curitiba",
                "80000-000"
        );

        Responsavel novo = new Responsavel();
        novo.setIdResponsavel(1L);
        novo.setNome("Maria");
        novo.setEndereco(novoEnd);

        when(repository.findById(1L)).thenReturn(Optional.of(responsavel));
        when(repository.save(any(Responsavel.class))).thenReturn(novo);

        Responsavel atualizado = service.alterarResponsavel(novo, 1L);

        assertEquals("Maria", atualizado.getNome());
        assertEquals("Rua Nova", atualizado.getEndereco().getLogradouro());
        assertEquals("Curitiba", atualizado.getEndereco().getCidade());

        verify(repository).findById(1L);
        verify(repository).save(any(Responsavel.class));
    }

}
