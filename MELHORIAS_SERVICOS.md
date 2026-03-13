# Melhorias para camada de servicos (safeway-core-api)

## Objetivo
Padronizar a camada de servicos para:
- retornar entidades no uso interno entre servicos;
- manter DTO apenas na borda HTTP (controllers);
- reduzir duplicacao de regra de negocio;
- melhorar manutencao, teste e observabilidade.

---

## Diagnostico atual (pontos observados)
1. **Contrato inconsistente nos servicos**
   - Alguns metodos retornam entidade (`ResponsavelService`, parte de `EventoService`).
   - Outros retornam DTO (`EnderecoService`, `EscolaService`, `ItinerarioService`, parte de `AlunoService`).
2. **DTO dentro da camada de dominio/aplicacao**
   - Conversoes `fromEntity` e `mapper` estao acontecendo dentro de servicos.
3. **Excecoes genericas**
   - Uso recorrente de `RuntimeException` com mensagens de string em varios servicos.
4. **Criacao manual de dependencia Spring**
   - Em `EscolaService`, ha uso de `new CurrentUserService()` em vez de injecao.
5. **Duplicacao de montagem de endereco**
   - Blocos de preenchimento de `Endereco` aparecem repetidos em `AlunoService`, `EscolaService` e `EnderecoService`.
6. **Risco transacional com chamadas externas**
   - Geocoding e outras chamadas externas podem ocorrer dentro de fluxo transacional.

---

## Regra-alvo de arquitetura
- **Controller**: recebe request DTO, chama service, converte entidade para response DTO.
- **Service (aplicacao)**: aplica regra de negocio, autorizacao de escopo, transacao e retorna entidade.
- **Repository**: persistencia e consultas.
- **Mapper/Assembler**: conversao DTO <-> entidade (preferencialmente fora da regra de negocio).

Resumo pratico:
- DTO entra e sai na API.
- Entidade circula entre servicos.

---

## Padrao sugerido para retorno de metodos

### 1) Para comandos (create/update)
- Retornar a **entidade persistida**.
- Exemplo:
  - antes: `EnderecoResponse criar(EnderecoRequest request)`
  - depois: `Endereco criar(EnderecoRequest request)`

### 2) Para consultas internas
- Retornar **entidade** ou colecao de entidades.
- Exemplo:
  - antes: `List<EnderecoResponse> listarPorResponsavel(UUID responsavelId)`
  - depois: `List<Endereco> listarPorResponsavel(UUID responsavelId)`

### 3) Para endpoint HTTP
- Controller converte resultado:
  - `return ResponseEntity.ok(EnderecoResponse.fromEntity(enderecoService.criar(request)));`

---

## Melhorias priorizadas

## P0 (fazer primeiro)
1. **Separar contrato interno de contrato HTTP**
   - Migrar servicos para retorno de entidade.
   - Mover conversao DTO para controllers ou mappers de apresentacao.

2. **Trocar `RuntimeException` por excecoes de dominio**
   - Criar excecoes como:
     - `ResourceNotFoundException`
     - `BusinessRuleException`
     - `ForbiddenResourceAccessException`
     - `ExternalServiceException`
   - Atualizar `GlobalExceptionHandler` para mapear status HTTP corretos (404, 403, 422, 409, 502).

3. **Corrigir injecao de dependencia em `EscolaService`**
   - Remover `new CurrentUserService()` e usar `currentUserService` injetado.

4. **Padronizar validacao de ownership por usuario logado**
   - Centralizar em metodos `getOwnedOrThrow(...)` para todas entidades chave.

## P1 (alto impacto)
5. **Extrair logica de endereco para componente dedicado**
   - Ex.: `EnderecoFactory` (montagem) e/ou `EnderecoDomainService` (regra de negocio).
   - Eliminar repeticao em `AlunoService`, `EscolaService`, `EnderecoService`.

6. **Rever limites de transacao**
   - Evitar chamada externa dentro de transacao longa quando possivel.
   - Para geocoding:
     - opcao A: calcular antes de salvar;
     - opcao B: salvar e enfileirar recalc assinc.

7. **Padronizar semantica de metodo**
   - `buscarPorId`, `atualizar`, `desativar`, `deletar` devem seguir nomes e comportamento consistentes.
   - Evitar misturar `retornarUm`, `getById`, `buscarEntidade` para o mesmo papel.

8. **Adotar readOnly em consultas**
   - Anotar metodos de leitura com `@Transactional(readOnly = true)` quando necessario.

## P2 (evolucao)
9. **Criar facade/application services para fluxos compostos**
   - Ex.: cadastro completo de aluno com responsaveis/endereco em um caso de uso dedicado.

10. **Melhorar observabilidade**
    - Log estruturado com contexto (`userId`, `resourceId`, operacao).
    - Correlation id em logs de entrada/saida.

11. **Eventos de dominio mais claros**
    - Publicar eventos apos persistencia e com payload minimo e estavel.

---

## Recomendacoes por servico (foco no seu cenario)

### `EnderecoService`
- Retornar `Endereco` / `List<Endereco>` internamente.
- Deixar `EnderecoResponse` no controller.
- Trocar `RuntimeException` por excecoes especificas.
- Tratar falha de geocoding como erro externo especifico.

### `EscolaService`
- Corrigir uso de `CurrentUserService` injetado.
- Retornar `Escola` e `Endereco` em metodos internos.
- Deixar `EscolaResponse` e `EnderecoResponse` na controller.

### `AlunoService`
- Reduzir metodo `cadastrarAlunoCompleto` em componentes menores (responsavel, endereco, vinculo).
- Extrair criacao/atualizacao de endereco para componente unico.
- Garantir consistencia de eventos publicados (quando e com qual payload).

### `EventoService`
- Evitar usar `EventoRequest` como response do service; criar `EventoResponse` na camada API.
- Padronizar retorno interno para `Evento`.
- Cobrir filtros e defaults (`priority`) com testes de unidade.

### `ItinerarioService`
- Retorno interno como entidade em metodos reutilizaveis.
- Manter mapper de response fora da regra principal.
- Revisar validacoes e excecoes para dominio.

---

## Estrategia de migracao (incremental e segura)

### Fase 1 - Contratos internos
- Introduzir metodos internos retornando entidade.
- Manter metodos antigos temporariamente (deprecados) para reduzir impacto.

### Fase 2 - Controllers
- Atualizar controllers para converter entidade -> DTO.
- Remover conversoes DTO de dentro dos servicos.

### Fase 3 - Excecoes e handler
- Introduzir excecoes tipadas.
- Atualizar `GlobalExceptionHandler` para payload padrao de erro.

### Fase 4 - Limpeza
- Remover metodos deprecados.
- Padronizar nomes e assinaturas.

---

## Testes minimos recomendados
1. **Unitario por service**
   - regra de ownership;
   - not found;
   - regra de negocio.
2. **Integracao por endpoint**
   - status code correto para sucesso/erro.
3. **Regressao de migracao**
   - garantir que retorno HTTP permaneceu estavel apos mover mapeamento para controller.

---

## Exemplo de template para novos servicos

```java
@Service
@RequiredArgsConstructor
public class ExemploService {

    private final ExemploRepository repository;

    @Transactional
    public Exemplo criar(Exemplo entidade) {
        // regras
        return repository.save(entidade);
    }

    @Transactional(readOnly = true)
    public Exemplo buscarOwned(UUID id, UUID userId) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Exemplo nao encontrado"));
    }
}
```

No controller:

```java
@PostMapping
public ResponseEntity<ExemploResponse> criar(@Valid @RequestBody ExemploRequest request) {
    Exemplo salvo = exemploService.criar(ExemploMapper.toEntity(request));
    return ResponseEntity.status(HttpStatus.CREATED).body(ExemploResponse.fromEntity(salvo));
}
```

---

## Checklist rapido de execucao
- [ ] Definir padrao oficial: service retorna entidade, controller retorna DTO.
- [ ] Ajustar `EnderecoService`, `EscolaService`, `ItinerarioService`, `AlunoService`, `EventoService`.
- [ ] Criar excecoes tipadas + atualizar `GlobalExceptionHandler`.
- [ ] Extrair componente compartilhado de endereco.
- [ ] Cobrir com testes unitarios e de integracao os fluxos alterados.

