package com.safeway.tech.services;

import com.safeway.tech.dto.EscolaComAlunosResponse;
import com.safeway.tech.dto.EscolaRequest;
import com.safeway.tech.dto.EscolaResponse;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.EscolaRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public EscolaResponse cadastrarEscola(EscolaRequest request) {
        Long usuarioId = new CurrentUserService().getCurrentUserId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Criar endereço
        Endereco endereco = new Endereco();
        endereco.setLogradouro(request.endereco().logradouro());
        endereco.setNumero(request.endereco().numero());
        endereco.setComplemento(request.endereco().complemento());
        endereco.setBairro(request.endereco().bairro());
        endereco.setCidade(request.endereco().cidade());
        endereco.setUf(request.endereco().uf());
        endereco.setCep(request.endereco().cep());
        endereco.setLatitude(request.endereco().latitude());
        endereco.setLongitude(request.endereco().longitude());
        endereco.setTipo("ESCOLA");
        endereco.setAtivo(true);
        endereco.setPrincipal(true);

        endereco = enderecoRepository.save(endereco);

        // Criar escola
        Escola escola = new Escola();
        escola.setUsuario(usuario);
        escola.setEndereco(endereco);
        escola.setNome(request.nome());
        escola.setNivelEnsino(request.nivelEnsino());

        escola = escolaRepository.save(escola);

        return EscolaResponse.fromEntity(escola);
    }

    public List<EscolaComAlunosResponse> listarEscolasComAlunos() {
        Long usuarioId = new CurrentUserService().getCurrentUserId();

        return escolaRepository.findByUsuarioIdUsuario(usuarioId).stream()
                .map(EscolaComAlunosResponse::fromEntity)
                .toList();
    }
}