package com.safeway.tech.services;

import com.safeway.tech.dto.TransporteRequest;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.TransporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransporteService {
    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Transporte getById(Long idTransporte){
        return  transporteRepository.findById(idTransporte)
                .orElseThrow(() -> new RuntimeException("Transporte não encontrado"));
    }

    public Transporte cadastrarTransporte(TransporteRequest request){
        transporteRepository.findByPlaca(request.placa()).ifPresent(t -> {
            throw new RuntimeException("Transporte com placa já cadastrada");
        });

        Transporte transporte = new Transporte();
        transporte.setPlaca(request.placa());
        transporte.setModelo(request.modelo());
        transporte.setCapacidade(request.capacidade());

        // Todo adicionar o link com o usuário

        return transporteRepository.save(transporte);
    }

    public Transporte buscarTransportePorUsuário(Long idUsuario){
        Usuario usuario = usuarioService.retornarUm(idUsuario);
        return transporteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("O usuário não possui nenhum transporte associado"));
    }

    public void excluirTransporte(Long idTransporte){
        transporteRepository.deleteById(idTransporte);
    }

    public Transporte alterarTransporte(TransporteRequest request, Long id){
     Transporte transporte = getById(id);

     transporte.setPlaca(request.placa());
     transporte.setModelo(request.modelo());
     transporte.setCapacidade(request.capacidade());

     return transporteRepository.save(transporte);
    }
}
