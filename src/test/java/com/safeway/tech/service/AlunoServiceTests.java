package com.safeway.tech.service;

import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.models.*;
import com.safeway.tech.repository.*;
import com.safeway.tech.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock private AlunoRepository alunoRepository;
    @Mock private ResponsavelRepository responsavelRepository;
    @Mock private EnderecoRepository enderecoRepository;
    @Mock private EscolaRepository escolaRepository;
    @Mock private TransporteRepository transporteRepository;

    private Escola escola;
    private Transporte transporte;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        escola = new Escola();
        escola.setIdEscola(1L);
        escola.setNome("Escola XPTO");

        transporte = new Transporte();
        transporte.setIdTransporte(1L);
        transporte.setPlaca("AG0N-11A");
    }

    @Test
    void deveCadastrarAlunoCompletoComTransporte() {
        var respEnderecoReq = new CadastroAlunoCompletoRequest.EnderecoData("Vale dos pinherais","312",
                "Casa","Matriz","Maua","São paulo","09100-200");

        var respData = new CadastroAlunoCompletoRequest.ResponsavelComEnderecoData(
                "Maria", "12345678900", "99999-8888", "2222-3333",
                "maria@email.com",
                respEnderecoReq
        );

        var request = new CadastroAlunoCompletoRequest(
                "João", "Prof. Ana", LocalDate.of(2015, 1, 1), 5,
                "A", 450.50, 10, 1L, 1L,
                List.of(respData)
        );

        // ------- MOCKS -------
        when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));
        when(transporteRepository.findById(1L)).thenReturn(Optional.of(transporte));
        when(alunoRepository.save(any())).thenAnswer(inv -> {
            Aluno a = inv.getArgument(0, Aluno.class);
            a.setIdAluno(10L);
            return a;
        });

        when(enderecoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(responsavelRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        // ------- EXECUÇÃO -------
        Long idGerado = alunoService.cadastrarAlunoCompleto(request);

        // ------- VALIDAÇÃO -------
        assertEquals(10L, idGerado);
        verify(escolaRepository).findById(1L);
        verify(transporteRepository).findById(1L);
        verify(alunoRepository).save(any(Aluno.class));
        verify(enderecoRepository).save(any(Endereco.class));
        verify(responsavelRepository).save(any(Responsavel.class));
    }


    // ==========================================================
    // TESTE 2: Cadastro de aluno sem transporte
    // ==========================================================
    @Test
    void deveCadastrarAlunoSemTransporte() {
        var request = new CadastroAlunoCompletoRequest(
                "José", "Prof. Bruno", LocalDate.now(), 4,
                "B", 300.0, 15, 1L, null, null
        );

        when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));
        when(alunoRepository.save(any())).thenAnswer(inv -> {
            Aluno a = inv.getArgument(0, Aluno.class);
            a.setIdAluno(20L);
            return a;
        });

        Long id = alunoService.cadastrarAlunoCompleto(request);

        assertEquals(20L, id);
        verify(transporteRepository, never()).findById(any());
    }


    // ==========================================================
    // TESTE 3: Escola não encontrada
    // ==========================================================
    @Test
    void deveLancarErroQuandoEscolaNaoEncontrada() {
        var request = new CadastroAlunoCompletoRequest(
                "Teste", "Prof", LocalDate.now(), 3,
                "C", 200.0, 5, 9999L, null, null
        );

        when(escolaRepository.findById(9999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alunoService.cadastrarAlunoCompleto(request));
    }


    // ==========================================================
    // TESTE 4: Transporte não encontrado
    // ==========================================================
    @Test
    void deveLancarErroQuandoTransporteNaoEncontrado() {
        var request = new CadastroAlunoCompletoRequest(
                "Teste", "Prof", LocalDate.now(), 3,
                "C", 200.0, 5, 1L, 8888L, null
        );

        when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));
        when(transporteRepository.findById(8888L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alunoService.cadastrarAlunoCompleto(request));
    }


    // ==========================================================
    // TESTE 5: Deve cadastrar aluno com mais de um responsável
    // ==========================================================
    @Test
    void deveCadastrarAlunoComDoisResponsaveis() {
        var endReq = new CadastroAlunoCompletoRequest.EnderecoData("Vale dos pinherais","312",
                "Casa","Matriz","Maua","São paulo","09100-200");

        var r1 = new CadastroAlunoCompletoRequest.ResponsavelComEnderecoData(
                "Pai", "111", "999", "888", "pai@mail.com", endReq
        );
        var r2 = new CadastroAlunoCompletoRequest.ResponsavelComEnderecoData(
                "Mãe", "222", "777", "666", "mae@mail.com", endReq
        );

        var request = new CadastroAlunoCompletoRequest(
                "João", "Prof", LocalDate.now(), 2,
                "A", 500.0, 12, 1L, null, List.of(r1, r2)
        );

        when(escolaRepository.findById(1L)).thenReturn(Optional.of(escola));
        when(alunoRepository.save(any())).thenAnswer(inv -> {
            Aluno a = inv.getArgument(0, Aluno.class);
            a.setIdAluno(30L);
            return a;
        });

        when(enderecoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(responsavelRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Long id = alunoService.cadastrarAlunoCompleto(request);

        assertEquals(30L, id);
        verify(responsavelRepository, times(2)).save(any());
        verify(enderecoRepository, times(2)).save(any());
    }

}
