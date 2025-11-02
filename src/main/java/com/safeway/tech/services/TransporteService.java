package com.safeway.tech.services;

import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
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
        return getOwnedOrThrow(idTransporte);
    }

    public Transporte salvarTransporte(Transporte transporte){
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);
        // salva transporte primeiro
        Transporte salvo = repository.save(transporte);
        // vincula ao usuário (lado dono da FK)
        usuario.setTransporte(salvo);
        usuarioRepository.save(usuario);
        System.out.println("Transporte cadastrado!");
        return salvo;
    }

    public List<Transporte> listarTransportes(){
        Long userId = currentUserService.getCurrentUserId();
        return repository.findAllByUsuario_IdUsuario(userId);
    }

    public void excluirTransporte(long idTransporte){
        Transporte transporte = getOwnedOrThrow(idTransporte);
        // remove vínculo do usuário
        Usuario usuario = transporte.getUsuario();
        if (usuario != null) {
            usuario.setTransporte(null);
            usuarioRepository.save(usuario);
        }
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
}
