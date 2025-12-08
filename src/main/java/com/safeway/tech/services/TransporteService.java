package com.safeway.tech.services;

import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransporteService {
    @Autowired
    private TransporteRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CurrentUserService currentUserService;

    private Transporte getOwnedOrThrow(long idTransporte){
        Long userId = currentUserService.getCurrentUserId();
        // Como o proprietário está no lado do Usuario (OneToOne), validamos via join
        Transporte t = repository.findById(idTransporte)
                .orElseThrow(() -> new RuntimeException("Transporte não encontrado"));
        if (t.getUsuario() == null || !t.getUsuario().getIdUsuario().equals(userId)) {
            throw new RuntimeException("Sem permissão para acessar este transporte");
        }
        return t;
    }

    public Transporte getById(long idTransporte){
        Transporte t = getOwnedOrThrow(idTransporte);
        // inicializa coleção lazy de alunos e seus relacionamentos usados pelos DTOs
        if (t.getAlunosTransportes() != null) {
            int ignored = t.getAlunosTransportes().size();
            for (Aluno a : t.getAlunosTransportes()) {
                // inicializa lista de responsáveis
                if (a.getResponsaveis() != null) {
                    int ignored2 = a.getResponsaveis().size();
                }
                // inicializa escola (ManyToOne)
                if (a.getEscola() != null) {
                    // acessar um campo para forçar fetch
                    String nomeEscola = a.getEscola().getNome();
                    // evitar warning unused
                    if (nomeEscola == null) { /* noop */ }
                }
            }
        }
        return t;
    }

    public Transporte salvarTransporte(Transporte transporte){
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);

        transporte.setUsuario(usuario);
        Transporte salvo = repository.save(transporte);

        System.out.println("Transporte cadastrado!");
        return salvo;
    }

    public List<Transporte> listarTransportes(){
        Long userId = currentUserService.getCurrentUserId();
        return repository.findAllByUsuario_IdUsuario(userId);
    }

    public void excluirTransporte(long idTransporte){
        Transporte transporte = getOwnedOrThrow(idTransporte);
        repository.delete(transporte);
    }

    public Transporte alterarTransporte(Transporte transporte, long idTransporte){
        Transporte existente = getOwnedOrThrow(idTransporte);
        existente.setPlaca(transporte.getPlaca());
        existente.setModelo(transporte.getModelo());
        existente.setCapacidade(transporte.getCapacidade());
        System.out.println("Transporte Atualizado!");
        return repository.save(existente);
    }

    // Novo: retorna alunos vinculados ao transporte garantindo inicialização dentro da transação
    public List<Aluno> listarAlunos(long idTransporte) {
        Transporte t = getOwnedOrThrow(idTransporte);
        List<Aluno> alunos = t.getAlunosTransportes();
        // força inicialização da coleção lazy
        if (alunos != null) {
            int ignored = alunos.size();
            for (Aluno a : alunos) {
                if (a.getResponsaveis() != null) {
                    int ignored2 = a.getResponsaveis().size();
                }
                if (a.getEscola() != null) {
                    String nomeEscola = a.getEscola().getNome();
                    if (nomeEscola == null) { /* noop */ }
                }
            }
        }
        return alunos;
    }
}
