package com.safeway.tech.services;

import com.safeway.tech.dto.MensalidadeRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.AlunoTransporte;
import com.safeway.tech.models.Mensalidade;
import com.safeway.tech.repository.MensalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensalidadeService {

    @Autowired
    private MensalidadeRepository mensalidadeRepository;

    @Autowired
    private AlunoTransporteService alunoTransporteService;

    @Autowired
    private AlunoService alunoService;

    public Mensalidade cadastrarMensalidade(MensalidadeRequest request) {
        Mensalidade mensalidade = new Mensalidade();
        mensalidade.setValorMensalidade(request.valorMensalidade());
        mensalidade.setDataMensalidade(request.dataMensalidade());

        AlunoTransporte alunoTransporte = alunoTransporteService.buscarPorId(request.idAlunoTransporte());

        mensalidade.setAlunoTransporte(alunoTransporte);

        return mensalidadeRepository.save(mensalidade);
    }

    public Mensalidade buscarMensalidadePorId(Long id) {
        Mensalidade mensalidade = mensalidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensalidade não encontrada com o ID: " + id));
        return mensalidade;
    }

    public List<Mensalidade> listarMensalidadesPorAluno(Long idAluno) {
        Aluno aluno =
        return mensalidadeRepository.findByAlunoTransporte(alunoTransporte);
    }

    public List<Mensalidade> listarTodasMensalidades(Long id) {
        AlunoTransporte alunoTransporte = alunoTransporteService.buscarPorId(id);
        mensalidadeRepository.findAllByUsuario(id);
    }

    public Mensalidade atualizarMensalidade(Long id, MensalidadeRequest request) {
        return null;
    }

    public void deletarMensalidade(Long id) {
        mensalidadeRepository.deleteById(id);
    }

}
