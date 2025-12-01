package com.safeway.tech.services;

import com.safeway.tech.dto.AlunoResponse;
import com.safeway.tech.dto.AlunoUpdateRequest;
import com.safeway.tech.dto.CadastroAlunoCompletoRequest;
import com.safeway.tech.models.Aluno;
import com.safeway.tech.models.Endereco;
import com.safeway.tech.models.Escola;
import com.safeway.tech.models.Responsavel;
import com.safeway.tech.models.Transporte;
import com.safeway.tech.models.Usuario;
import com.safeway.tech.repository.AlunoRepository;
import com.safeway.tech.repository.EnderecoRepository;
import com.safeway.tech.repository.EscolaRepository;
import com.safeway.tech.repository.ResponsavelRepository;
import com.safeway.tech.repository.TransporteRepository;
import com.safeway.tech.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final EnderecoRepository enderecoRepository;
    private final EscolaRepository escolaRepository;
    private final TransporteRepository transporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CurrentUserService currentUserService;
    private final EnderecoService enderecoService;

    @Transactional
    public Long cadastrarAlunoCompleto(CadastroAlunoCompletoRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        Usuario usuario = usuarioRepository.getReferenceById(userId);

        // 1. Criar o aluno
        Aluno aluno = new Aluno();
        aluno.setUsuario(usuario);
        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setValorMensalidade(request.valorMensalidade());
        aluno.setDiaVencimento(request.diaVencimento());
        aluno.setAtivo(true);

        // 2. Buscar escola do próprio usuário
        Escola escola = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(request.fkEscola(), userId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
        aluno.setEscola(escola);

        // 3. Transporte (opcional) do próprio usuário
        if (request.fkTransporte() != null) {
            Transporte transporte = transporteRepository.findByIdTransporteAndUsuario_IdUsuario(request.fkTransporte(), userId)
                    .orElseThrow(() -> new RuntimeException("Transporte não encontrado para este usuário"));
            aluno.setTransporte(transporte);
        }

        aluno = alunoRepository.save(aluno);
        final Long alunoId = aluno.getIdAluno(); // usado nas lambdas

        // 4. Processar responsáveis e endereços (reutilizando por CPF)
        var responsaveis = request.responsaveis();
        if (responsaveis != null) {
            for (CadastroAlunoCompletoRequest.ResponsavelComEnderecoData respData : responsaveis) {
                String cpf = respData.cpf();

                Responsavel responsavel;
                if (cpf != null && !cpf.isBlank()) {
                    Optional<Responsavel> existenteOpt = responsavelRepository
                            .findByCpfAndUsuario_IdUsuario(cpf, userId);
                    if (existenteOpt.isPresent()) {
                        // 4.x Reutiliza responsável já existente (mesmo CPF para o mesmo usuário)
                        responsavel = existenteOpt.get();
                    } else {
                        // 4.1 Criar endereço
                        Endereco endereco = new Endereco();
                        endereco.setLogradouro(respData.endereco().logradouro());
                        endereco.setNumero(respData.endereco().numero());
                        endereco.setComplemento(respData.endereco().complemento());
                        endereco.setBairro(respData.endereco().bairro());
                        endereco.setCidade(respData.endereco().cidade());
                        endereco.setUf(respData.endereco().uf());
                        endereco.setCep(respData.endereco().cep());
                        endereco.setLatitude(respData.endereco().latitude());
                        endereco.setLongitude(respData.endereco().longitude());
                        endereco.setTipo(respData.endereco().tipo() != null ? respData.endereco().tipo() : "RESIDENCIAL");
                        endereco.setAtivo(true);
                        endereco.setPrincipal(true);
                        endereco = enderecoService.calcularCoordenadas(endereco);
                        endereco = enderecoRepository.save(endereco);

                        // 4.2 Criar responsável com vínculo ao usuário
                        responsavel = new Responsavel();
                        responsavel.setUsuario(usuario);
                        responsavel.setNome(respData.nome());
                        responsavel.setCpf(cpf);
                        responsavel.setTel1(respData.tel1());
                        responsavel.setTel2(respData.tel2());
                        responsavel.setEmail(respData.email());
                        responsavel.setEndereco(endereco);
                        responsavel = responsavelRepository.save(responsavel);
                    }
                } else {
                    // CPF não informado: sempre cria um novo responsável
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(respData.endereco().logradouro());
                    endereco.setNumero(respData.endereco().numero());
                    endereco.setComplemento(respData.endereco().complemento());
                    endereco.setBairro(respData.endereco().bairro());
                    endereco.setCidade(respData.endereco().cidade());
                    endereco.setUf(respData.endereco().uf());
                    endereco.setCep(respData.endereco().cep());
                    endereco.setLatitude(respData.endereco().latitude());
                    endereco.setLongitude(respData.endereco().longitude());
                    endereco.setTipo(respData.endereco().tipo() != null ? respData.endereco().tipo() : "RESIDENCIAL");
                    endereco.setAtivo(true);
                    endereco.setPrincipal(true);
                    endereco = enderecoService.calcularCoordenadas(endereco);
                    endereco = enderecoRepository.save(endereco);

                    responsavel = new Responsavel();
                    responsavel.setUsuario(usuario);
                    responsavel.setNome(respData.nome());
                    responsavel.setCpf(cpf);
                    responsavel.setTel1(respData.tel1());
                    responsavel.setTel2(respData.tel2());
                    responsavel.setEmail(respData.email());
                    responsavel.setEndereco(endereco);
                    responsavel = responsavelRepository.save(responsavel);
                }

                // 4.3 Vincular aluno <-> responsável e persistir a associação
                if (responsavel.getAlunos().stream().noneMatch(a -> a.getIdAluno().equals(alunoId))) {
                    responsavel.getAlunos().add(aluno);
                }
                responsavel = responsavelRepository.save(responsavel); // persistir join table

                final Long respId = responsavel.getIdResponsavel();
                if (aluno.getResponsaveis().stream().noneMatch(r -> r.getIdResponsavel().equals(respId))) {
                    aluno.getResponsaveis().add(responsavel);
                }
            }
            // salvar aluno novamente para garantir que a relação inversa está persistida
            alunoRepository.save(aluno);
        }

        return aluno.getIdAluno();
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    @Transactional
    public AlunoResponse obterDadosAluno(Long alunoId) {
        // Validação de escopo: garante que o aluno pertence ao usuário logado
        Long userId = currentUserService.getCurrentUserId();
        Aluno aluno = alunoRepository.findByIdAlunoAndUsuario_IdUsuario(alunoId, userId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para este usuário"));
        return AlunoResponse.fromEntity(aluno);
    }

    @Transactional
    public AlunoResponse atualizarAluno(Long alunoId, AlunoUpdateRequest request) {
        Long userId = currentUserService.getCurrentUserId();

        Aluno aluno = alunoRepository.findByIdAlunoAndUsuario_IdUsuario(alunoId, userId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para este usuário"));

        // Atualiza dados basicos do aluno
        aluno.setNome(request.nome());
        aluno.setProfessor(request.professor());
        aluno.setDtNascimento(request.dtNascimento());
        aluno.setSerie(request.serie());
        aluno.setSala(request.sala());
        aluno.setValorMensalidade(request.valorMensalidade());
        aluno.setDiaVencimento(request.diaVencimento());

        Escola escola = escolaRepository.findByIdEscolaAndUsuario_IdUsuario(request.fkEscola(), userId)
                .orElseThrow(() -> new RuntimeException("Escola não encontrada para este usuário"));
        aluno.setEscola(escola);

        if (request.fkTransporte() != null) {
            Transporte transporte = transporteRepository.findByIdTransporteAndUsuario_IdUsuario(request.fkTransporte(), userId)
                    .orElseThrow(() -> new RuntimeException("Transporte não encontrado para este usuário"));
            aluno.setTransporte(transporte);
        } else {
            aluno.setTransporte(null);
        }

        // Gerenciamento completo de responsaveis/endereco via aluno
        if (request.responsaveis() != null) {
            // Mapa rapido dos responsaveis atuais por id (se precisar no futuro)
            // java.util.Map<Long, Responsavel> atuaisPorId = aluno.getResponsaveis().stream()
            //         .filter(r -> r.getIdResponsavel() != null)
            //         .collect(java.util.stream.Collectors.toMap(Responsavel::getIdResponsavel, r -> r));

            java.util.List<Responsavel> novaLista = new java.util.ArrayList<>();

            for (AlunoUpdateRequest.ResponsavelUpdateData dto : request.responsaveis()) {
                Responsavel responsavel;

                if (dto.idResponsavel() != null) {
                    // Ja existe: carrega garantindo escopo do usuario
                    responsavel = responsavelRepository
                            .findByIdResponsavelAndUsuario_IdUsuario(dto.idResponsavel(), userId)
                            .orElseThrow(() -> new RuntimeException("Responsável não encontrado para este usuário"));

                    if (dto.deletar()) {
                        // Remover vinculo aluno<->responsavel
                        final Long idAlunoParam = alunoId;
                        responsavel.getAlunos().removeIf(a -> a.getIdAluno().equals(idAlunoParam));
                        // Se não tiver mais alunos vinculados, pode deletar o responsavel inteiro
                        if (responsavel.getAlunos().isEmpty()) {
                            responsavelRepository.delete(responsavel);
                        } else {
                            responsavelRepository.save(responsavel);
                        }
                        // Não adiciona na nova lista -> deixa de estar vinculado a este aluno
                        continue;
                    }
                } else {
                    // Novo responsavel
                    responsavel = new Responsavel();
                    Usuario usuario = usuarioRepository.getReferenceById(userId);
                    responsavel.setUsuario(usuario);
                    responsavel.setAlunos(new java.util.ArrayList<>());
                }

                // Atualiza dados do responsavel
                responsavel.setNome(dto.nome());
                responsavel.setCpf(dto.cpf());
                responsavel.setTel1(dto.tel1());
                responsavel.setTel2(dto.tel2());
                responsavel.setEmail(dto.email());

                // Atualiza/cria endereco vinculado ao responsavel
                AlunoUpdateRequest.EnderecoUpdateData endDto = dto.endereco();
                Endereco endereco = responsavel.getEndereco();
                if (endereco == null) {
                    endereco = new Endereco();
                }
                endereco.setLogradouro(endDto.logradouro());
                endereco.setNumero(endDto.numero());
                endereco.setComplemento(endDto.complemento());
                endereco.setBairro(endDto.bairro());
                endereco.setCidade(endDto.cidade());
                endereco.setUf(endDto.uf());
                endereco.setCep(endDto.cep());
                endereco.setLatitude(endDto.latitude());
                endereco.setLongitude(endDto.longitude());
                endereco.setTipo(endDto.tipo() != null ? endDto.tipo() : "RESIDENCIAL");
                endereco.setAtivo(true);
                if (endereco.getIdEndereco() == null) {
                    endereco.setPrincipal(true);
                }
                endereco = enderecoService.calcularCoordenadas(endereco);
                endereco = enderecoRepository.save(endereco);
                responsavel.setEndereco(endereco);

                // Garante vinculo aluno<->responsavel nas duas pontas
                final Long idAlunoParam = alunoId;
                if (responsavel.getAlunos().stream().noneMatch(a -> a.getIdAluno().equals(idAlunoParam))) {
                    responsavel.getAlunos().add(aluno);
                }

                responsavel = responsavelRepository.save(responsavel);
                novaLista.add(responsavel);
            }

            // Substitui lista de responsaveis do aluno pelo novo conjunto
            aluno.setResponsaveis(novaLista);
        }

        aluno = alunoRepository.save(aluno);
        return AlunoResponse.fromEntity(aluno);
    }

    @Transactional
    public void deletarAluno(Long alunoId) {
        Long userId = currentUserService.getCurrentUserId();
        Aluno aluno = alunoRepository.findByIdAlunoAndUsuario_IdUsuario(alunoId, userId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado para este usuário"));

        // Remove vínculos aluno <-> responsáveis e deleta responsáveis órfãos
        if (aluno.getResponsaveis() != null && !aluno.getResponsaveis().isEmpty()) {
            for (Responsavel responsavel : aluno.getResponsaveis()) {
                // remove este aluno da lista de alunos do responsável
                responsavel.getAlunos().removeIf(a -> a.getIdAluno().equals(alunoId));

                if (responsavel.getAlunos().isEmpty()) {
                    // se não tiver mais alunos vinculados, apaga o responsável
                    responsavelRepository.delete(responsavel);
                } else {
                    // senão, apenas atualiza o vínculo
                    responsavelRepository.save(responsavel);
                }
            }
        }

        // agora é seguro deletar o aluno (não há mais registros na tabela de junção)
        alunoRepository.delete(aluno);
    }
}
