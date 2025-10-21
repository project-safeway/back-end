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
    private AlunoTransporteService alunoTransporteService;

    public Mensalidade cadastrarMensalidade(MensalidadeRequest request) {
        return null;
    }

}
