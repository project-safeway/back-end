package com.safeway.tech.services;

import com.safeway.tech.dto.MensalidadeRequest;
import com.safeway.tech.models.Mensalidade;
import com.safeway.tech.repository.MensalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensalidadeService {

    @Autowired
    private MensalidadeRepository mensalidadeRepository;

    @Autowired
    private ItinerarioService itinerarioService;

    @Autowired
    private AlunoService alunoService;

    public Mensalidade cadastrarMensalidade(MensalidadeRequest request) {
        Mensalidade mensalidade = new Mensalidade();
        mensalidade.setValorMensalidade(request.valorMensalidade());
        mensalidade.setDataMensalidade(request.dataMensalidade());

        return mensalidadeRepository.save(mensalidade);
    }

    public Mensalidade buscarMensalidadePorId(Long id) {
        Mensalidade mensalidade = mensalidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada com o ID: " + id));
        return mensalidade;
    }

    public Mensalidade atualizarMensalidade(Long id, MensalidadeRequest request) {
        return null;
    }

    public void deletarMensalidade(Long id) {
        mensalidadeRepository.deleteById(id);
    }

}
