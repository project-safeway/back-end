package com.safeway.tech;

import com.safeway.tech.dto.AlunoTransporteRequest;
import com.safeway.tech.services.AlunoService;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.EscolaRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import com.safeway.tech.repository.TransporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class AlunoServiceTest {

        @Mock
        private AlunoRepository alunoRepository;

        @Mock
        private ResponsavelRepository responsavelRepository;

        @Mock
        private EnderecoRepository enderecoRepository;

        @Mock
        private EscolaRepository escolaRepository;

        @Mock
        private TransporteRepository transporteRepository;

        private AlunoService alunoService;

        @Captor
        private ArgumentCaptor<Aluno> alunoCaptor;

        @Captor
        private ArgumentCaptor<Endereco> enderecoCaptor;

        @Captor
        private ArgumentCaptor<Responsavel> responsavelCaptor;

        @BeforeEach
        void setup() {
            alunoService = new AlunoService(
                    alunoRepository,
                    responsavelRepository,
                    enderecoRepository,
                    escolaRepository,
                    transporteRepository
            );
        }

        @Test
        void cadastrarAlunoCompleto_escolaNaoEncontrada_deveLancarRuntimeException() {
            // Arrange
            CadastroAlunoCompletoRequest request = new CadastroAlunoCompletoRequest(
                    "Nome", "Prof", LocalDate.now(), 1, "A",
                    BigDecimal.TEN, 1, 123L, null, null
            );

            when(escolaRepository.findById(123L)).thenReturn(Optional.empty());

            // Act & Assert
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> alunoService.cadastrarAlunoCompleto(request));
            assertEquals("Escola não encontrada", ex.getMessage());

            verify(escolaRepository).findById(123L);
            verifyNoMoreInteractions(alunoRepository, responsavel_repository(), enderecoRepository, transporteRepository);
        }

        @Test
        void cadastrarAlunoCompleto_transporteNaoEncontrado_deveLancarRuntimeException() {
            // Arrange
            CadastroAlunoCompletoRequest request = new CadastroAlunoCompletoRequest(
                    "Nome", "Prof", LocalDate.now(), 1, "A",
                    BigDecimal.TEN, 1, 1L, 2L, null
            );

            Escola escola = new Escola();
            escola.setIdEscola(1L);

            when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));
            when(transporte_repository().findById(2L)).thenReturn(Optional.empty());

            // Act & Assert
            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> alunoService.cadastrarAlunoCompleto(request));
            assertEquals("Transporte não encontrado", ex.getMessage());

            verify(escolaRepository).findById(1L);
            verify(transporte_repository()).findById(2L);
            verifyNoMoreInteractions(alunoRepository, responsavelRepository, enderecoRepository);
        }

        // helper to avoid accidental typos in verifyNoMoreInteractions (keeps messages clear)
        private TransporteRepository transporte_repository() {
            return transporteRepository;
        }

        private ResponsavelRepository responsavel_repository() {
            return responsavelRepository;
        }

        @Test
        void cadastrarAlunoCompleto_sucesso_semResponsaveis_semTransporte_deveSalvarAlunoERetornarId() {
            // Arrange
            CadastroAlunoCompletoRequest request = new CadastroAlunoCompletoRequest(
                    "João Silva",
                    "Prof. X",
                    LocalDate.of(2010, 5, 15),
                    5,
                    "A",
                    new BigDecimal("350.00"),
                    10,
                    1L,
                    null,
                    null
            );

            Escola escola = new Escola();
            escola.setIdEscola(1L);
            when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));

            // Simula comportamento do save: devolve a mesma instância com id populado
            when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> {
                Aluno a = invocation.getArgument(0);
                a.setIdAluno(100L);
                return a;
            });

            // Act
            Long resultId = alunoService.cadastrarAlunoCompleto(request);

            // Assert
            assertEquals(100L, resultId);

            verify(escolaRepository).findById(1L);
            verify(alunoRepository).save(alunoCaptor.capture());
            Aluno savedAluno = alunoCaptor.getValue();

            assertEquals("João Silva", savedAluno.getNome());
            assertEquals("Prof. X", savedAluno.getProfessor());
            assertEquals(LocalDate.of(2010, 5, 15), savedAluno.getDtNascimento());
            assertEquals(Integer.valueOf(5), savedAluno.getSerie());
            assertEquals("A", savedAluno.getSala());
            assertEquals(new BigDecimal("350.00"), savedAluno.getValorMensalidade());
            assertEquals(10, savedAluno.getDiaVencimento());
            assertTrue(savedAluno.getAtivo());
            assertSame(escola, savedAluno.getEscola());

            verifyNoMoreInteractions(responsavelRepository, enderecoRepository, transporteRepository);
        }

        @Test
        void cadastrarAlunoCompleto_sucesso_comResponsaveis_deveSalvarEnderecoResponsavelEAssociar() {
            // Arrange - monta DTO com 1 responsável + endereço
            CadastroAlunoCompletoRequest.EnderecoData enderecoData =
                    new CadastroAlunoCompletoRequest.EnderecoData("Rua A", "100", "Casa", "Centro", "CidadeX", "SP", "00000-000");

            CadastroAlunoCompletoRequest.ResponsavelComEnderecoData respData =
                    new CadastroAlunoCompletoRequest.ResponsavelComEnderecoData(
                            "Ana Oliveira",
                            "123.456.789-00",
                            "1111-1111",
                            "2222-2222",
                            "ana@example.com",
                            enderecoData
                    );

            CadastroAlunoCompletoRequest request = new CadastroAlunoCompletoRequest(
                    "Maria Oliveira",
                    "Prof. Y",
                    LocalDate.of(2012, 3, 20),
                    3,
                    "B",
                    new BigDecimal("200.00"),
                    5,
                    2L,
                    null,
                    List.of(respData)
            );

            Escola escola = new Escola();
            escola.setIdEscola(2L);
            when(escolaRepository.findById(2L)).thenReturn(Optional.of(escola));

            // Simula saves populando ids
            when(alunoRepository.save(any(Aluno.class))).thenAnswer(invocation -> {
                Aluno a = invocation.getArgument(0);
                a.setIdAluno(200L);
                return a;
            });
            when(enderecoRepository.save(any(Endereco.class))).thenAnswer(invocation -> {
                Endereco e = invocation.getArgument(0);
                e.setIdEndereco(300L);
                return e;
            });
            when(responsavelRepository.save(any(Responsavel.class))).thenAnswer(invocation -> {
                Responsavel r = invocation.getArgument(0);
                r.setIdResponsavel(400L);
                return r;
            });

            // Act
            Long resultId = alunoService.cadastrarAlunoCompleto(request);

            // Assert
            assertEquals(200L, resultId);

            // Verifica que endereco foi criado com os campos corretos
            verify(enderecoRepository).save(enderecoCaptor.capture());
            Endereco savedEndereco = enderecoCaptor.getValue();
            assertEquals("Rua A", savedEndereco.getLogradouro());
            assertEquals("100", savedEndereco.getNumero());
            assertEquals("Casa", savedEndereco.getComplemento());
            assertEquals("Centro", savedEndereco.getBairro());
            assertEquals("CidadeX", savedEndereco.getCidade());
            assertEquals("00000-000", savedEndereco.getCep());

            // Verifica que responsavel foi criado e associado ao endereco
            verify(responsavelRepository).save(responsavelCaptor.capture());
            Responsavel savedResponsavel = responsavelCaptor.getValue();
            assertEquals("Ana Oliveira", savedResponsavel.getNome());
            assertEquals("123.456.789-00", savedResponsavel.getCpf());
            assertEquals("1111-1111", savedResponsavel.getTel1());
            assertEquals("2222-2222", savedResponsavel.getTel2());
            assertEquals("ana@example.com", savedResponsavel.getEmail());
            assertNotNull(savedResponsavel.getEndereco());
            assertEquals(savedEndereco, savedResponsavel.getEndereco());

            // Verifica que o aluno salvo foi associado ao responsavel e vice-versa
            verify(alunoRepository, atLeastOnce()).save(alunoCaptor.capture());
            Aluno savedAluno = alunoCaptor.getAllValues().get(0); // primeira chamada de save é do aluno inicial
            assertTrue(savedResponsavel.getAlunos().contains(savedAluno));
            assertTrue(savedAluno.getResponsaveis().contains(savedResponsavel));
        }
    }
