# Migração do UniAura para a arquitetura Cloudsupport

Objetivo: deixar o projeto 100% de acordo com o manual (https://cloudsupport.dev/manual/).
Princípio central: **cada funcionalidade é um `@Service` (regra de negócio) exposto por um `@Ws` (webservice)**; o frontend só consome esses endpoints.

Pontos de partida medidos (outubro/2026): 46 entidades já estendem `BaseEntity` (via `SqlServerBaseEntity`), 51 services por entidade, 48 `JpaRepository`, 120 DTOs com sufixo `DTO`, ~46 `@Ws` em estilo CRUD REST com `id`, 1 teste.

Regras de ouro durante toda a migração:
- Migrar **um módulo por vez**, em um ramo próprio, sem quebrar o frontend (manter o endpoint antigo até o frontend trocar).
- Nada de migrar e refatorar ao mesmo tempo: primeiro o comportamento é igual, depois melhora.
- Cada funcionalidade migrada só está pronta quando cumpre o "Pronto" da Fase 3 (abaixo).

---

## Fase 0 — Decisões (CONCLUÍDA)

| # | Decisão | Resultado | Quem decidiu |
|---|---------|-----------|--------------|
| D1 | Autenticação | **Manter o login próprio** (`TokenService`, JWT emitido pelo projeto). Fica como **exceção documentada** ao manual, que prevê um provedor OIDC externo. A validação do JWT continua pela cloudsupport. | Usuário |
| D2 | Estrutura | **Um único microsserviço** (`erp.uniaura`) com os pacotes do manual: `persistence.entities`, `persistence.converters`, `services` (`web`, `jobs`, `common`), `remotes`, `util`. | Padrão do manual (confirmar) |
| D3 | Banco | **Liquibase** no lugar de `ddl-auto=update` (Fase 2, item 9, feito). | Padrão do manual (confirmar) |
| D4 | URLs do frontend | **camelCase**, porque manter o hífen é um desvio do manual. Mapa de renomeação abaixo. | Usuário |
| D5 | Git | **Adotar o fluxo do manual** (Fase 9): ramos `devNN`, `--no-ff`, sem rebase/squash, tags. | Padrão do manual (confirmar) |

D2, D3 e D5 não foram escolhidas explicitamente: adotei o padrão do manual, já que a meta é 100%. Se quiser abrir mão de alguma, é só mudar a linha da tabela antes da fase correspondente.

### Consequência da D1 (login próprio)
- Fase 6, item 4: em vez de remover o `TokenService`, **documentar a exceção** no README e manter o login.
- Continua valendo o restante da Fase 6: permissões `uniaura:grupo:privilegio` na claim `roles`, auditoria por claim e `allow-paths`.
- No frontend (Fase 8), o guarda do `AppShell` com token no `localStorage` continua; o `AuthRequired`/`useAuth` da cloudsupport só entram se o login passar a ser OIDC. Registrar como exceção também.

### Mapa de renomeação da D4 (executar na Fase 8)

Pastas de rota com hífen hoje (8):

| Hoje | Passa a ser |
|------|-------------|
| `app/portal-do-aluno` | `app/portalDoAluno` |
| `app/recuperar-senha` | `app/recuperarSenha` |
| `app/portal-do-aluno/biblioteca/meus-emprestimos` | `.../biblioteca/meusEmprestimos` |
| `app/portal-do-aluno/biblioteca/minhas-reservas` | `.../biblioteca/minhasReservas` |
| `app/web/termos-processo-seletivo` | `app/web/termosProcessoSeletivo` |
| `app/web/sobre/estrutura-fisica` | `app/web/sobre/estruturaFisica` |
| `app/web/sobre/premios-e-rankings` | `app/web/sobre/premiosERankings` |
| `app/web/sobre/regimento-universitario` | `app/web/sobre/regimentoUniversitario` |

Também com hífen e fora da regra: os 4 CSS (`design-system.css`, `erp-central.css`, `portal-aluno.css`, `primereact-tema.css`), que viram `designSystem.css`, `erpCentral.css`, `portalAluno.css`, `primereactTema.css`.

Não muda: os `slugs` de curso nas URLs dinâmicas (`/web/tecnologia/ciencia-da-computacao`). São dados, não nomes de pasta.

Impacto medido (referências a atualizar): `portal-do-aluno` em 52 pontos de 24 arquivos (menus, `auth.js`, redirecionamentos, rodapé, links), `recuperar-senha` em 3 pontos de 2 arquivos, e 1 a 2 pontos para cada uma das demais. O `basePath` (`/uniaura/app`), a API (`/api`) e o `localStorage` não são afetados.

Cuidados na execução:
1. Renomear a pasta e **todas** as referências no mesmo commit, depois conferir com busca textual que não sobrou o nome antigo.
2. Em `next.config.js`, criar **redirecionamentos permanentes** das URLs antigas para as novas, para favoritos e links já divulgados não quebrarem.
3. Como o sistema de arquivos do Windows ignora maiúsculas e o Linux não, renomear com `git mv` em duas etapas (nome temporário) para o Git registrar a troca, e testar em um ambiente case-sensitive antes de publicar.
4. Rodar as rotas principais (login, home do aluno, disciplinas, cadastro, recuperar senha, site) depois da troca.

Entregável da fase: decisões registradas (feito) e mapa de impacto levantado (feito). Próximo passo: **Fase 1**.

## Fase 1 — Preparação e rede de segurança

1. Criar o ramo `dev1` a partir do `main` e configurar o Git: `push.followTags=true`, `pull.rebase=false`, `core.autocrlf=false`.
2. Escrever **testes de contrato** dos endpoints atuais (login, `/alunos/me`, pré-matrícula, recuperação de senha) antes de mexer. Hoje há 1 teste.
3. Listar todos os endpoints atuais (Swagger `/api/v3/api-docs`) e o que o frontend chama (`api(` aparece 60 vezes). Esse inventário vira a lista de funcionalidades da Fase 3.
4. Configurar propriedades que o manual descreve: `cloudsupport.ws.path-prefix`, `cloudsupport.error.tracking-generator`, `cloudsupport.auditing.auditor-jwt-claim`, `cloudsupport.jobs.repository=jdbc`.
5. Conferir o `/actuator/health` (hoje devolve 503) e as probes `/livez` e `/readyz`.

## Fase 2 — Persistência (`persistence.entities` e `persistence.converters`) — EM ANDAMENTO

Status por item (ramo `dev1`; o backend compila, falta subir e rodar contra o banco):

- [x] 1. **Pacotes:** 87 arquivos (46 entidades e 41 enums) movidos para `erp.uniaura.persistence.entities`, sem subpacotes. O enum `Turno` existia duplicado (aluno e turma, idêntico) e foi unificado. 210 arquivos tiveram o import atualizado. Os 8 literais de enum em JPQL (`MultaRepository`, `PainelCoordenacaoRepository`) viraram parâmetros SpEL (`:#{T(...).ATIVA}`), para passarem pelo conversor.
- [x] 2. **uid:** campo `uid` (`Long`, 54 bits) em `SqlServerBaseEntity`, gerado no `@PrePersist` por `UidGenerator.nextLong()`, herdado pelas 46 entidades. **Falta rodar `docs/sql/fase2UidIndices.sql`** (preenche as linhas antigas e cria o índice único filtrado), depois de subir o backend uma vez.
- [ ] 3. **`OffsetDateTime`:** adiado de propósito. O tipo do campo na entidade, os DTOs e o JSON que o frontend lê mudam juntos; faz sentido trocar **junto com cada funcionalidade na Fase 3**, quando o DTO também vira `Params`/`Retorno`.
- [x] 4. **`List` → `Set`:** nada a fazer, as entidades já não têm coleções.
- [x] 5. **`@OneToOne`:** 7 trocados por `@ManyToOne(fetch = LAZY, optional = false)` (Aluno, Professor, Funcionario, Responsavel, Pagamento, Multa, PlanoEnsino), mantendo `unique = true` no `JoinColumn` e um comentário com a cardinalidade. `@ManyToMany` não existia.
- [x] 6. **`cascade` e `LAZY`:** nada a fazer, nenhuma entidade usa `cascade` e todas as associações já são `LAZY`.
- [x] 7. **`equals`/`hashCode`/`Comparable`/regras nas entidades:** nada a fazer (verificado).
- [x] 8. **Enums:** os 41 enums ganharam `getId()` e `getEnum(String)`, e cada um tem um `@Converter(autoApply = true)` em `persistence.converters`. O valor gravado continua sendo o **nome da constante** (o mesmo texto do `@Enumerated(STRING)` anterior), então **os dados do banco não mudam**. Os `@Enumerated` das entidades foram removidos.
- [x] 9. **Liquibase:** feito. `liquibase-core` entrou no `pom.xml`; o changelog está em `backend/src/main/resources/db/changelog` (`db.changelog-master.xml` inclui `changes/001-esquema-inicial.sql`, em SQL formatado do Liquibase). O SQL foi gerado pelo próprio Hibernate a partir das 48 entidades (dialeto SQL Server, sem acessar o banco) e tem 4 changesets: tabelas, chaves únicas, chaves estrangeiras e índices únicos filtrados de `uid` (um por tabela, `ux<Tabela>Uid`). `ddl-auto` passou de `update` para `validate`, então o Hibernate só confere o mapeamento. `docs/sql/acompanhamento-uid.sql` foi apagado (o item 004 do changelog o substitui). Como o banco pôde ser recriado, não há changelog de baseline: apague o `UniAuraErpDB` e crie vazio; o Liquibase monta tudo na primeira subida. Mudanças futuras entram como `002-...sql`, `003-...sql`, nunca editando um arquivo já executado.

Antes de dar a fase por concluída:
1. [x] Subir o backend e conferir que não há erro de mapeamento: **verificado em 13/10/2026** (Hibernate inicializou, 48 repositórios, 38 `@Ws` e 3 `@Job`). Falta exercitar as consultas com SpEL do `PainelCoordenacaoRepository` em tempo de execução.
2. ~~Rodar `docs/sql/fase2UidIndices.sql`~~ — os índices de `uid` agora nascem no changelog do Liquibase.
3. Testar login, `/alunos/me`, pré-matrícula e o painel da coordenação.
## Fase 3 — Camada de negócio (`services`) — receita por funcionalidade

Transformar cada **entidade-service** em **várias funcionalidades**. Exemplo: `AlunoService` vira `CadastrarAluno`, `AlterarAluno`, `PesquisarAlunos`, `DetalharAluno`, `InativarAluno`.

Receita (repetir para cada funcionalidade):
1. Criar o pacote `services.web.<assunto>.<nomeFuncionalidade>.v1` (um subpacote por versão).
2. Criar `<Funcionalidade>ServiceV1 extends BaseService`, `@Service`, com **um** método público e `@Transactional` (`readOnly = true` nas consultas). Sem `throws` nas assinaturas.
3. Criar os DTOs `<Funcionalidade>ParamsV1` (entrada) e `<Funcionalidade>RetornoV1` (saída), sem sufixo `DTO`. O service nunca devolve entidade.
4. Trocar o uso de `JpaRepository` por `findById`, `findByUid`, `findByAttr`, `findAll`, `cb()`/`em()` e Active Record (`persist()`, `merge()`, `remove()`). Paginação passa a ser parâmetro/retorno próprio (sem `Page`/`Pageable` do Spring Data).
5. Lançar erros de negócio com `cloudsupport.services.BusinessException`.
6. Quebrar métodos longos em `@Fragment` (`<Funcionalidade>XxxFragment`, mesmo pacote); lógica usada por duas ou mais funcionalidades vai para `@Component` em `services.common.<assunto>`.
7. Escrever teste da funcionalidade.

Pronto da funcionalidade: um service, uma classe `Ws`, DTOs no padrão, sem repositório Spring Data, sem entidade na API, com teste.

### Progresso da Fase 3

| Funcionalidade | Pacote | Estado |
|---|---|---|
| `PesquisarDisciplinasDoAluno` (piloto, **nova**) | `erp.uniaura.services.web.academico.pesquisarDisciplinasDoAluno.v1` | Feita: `ServiceV1` + `WsV1` + `RetornoV1` + `CatalogoFragmentV1`. Endpoint `GET /api/uniaura/pesquisarDisciplinasDoAluno.v1`. O catálogo vem de `resources/catalogo/cursos.json` (fonte única, no backend). O frontend a consome por `app/remotes/uniaura/academico/pesquisarDisciplinasDoAlunoRemote.js`. Falta testar rodando o backend. |

| `PesquisarCursos`, `DetalharCurso`, `CadastrarCurso`, `AlterarCurso`, `RemoverCurso` | `erp.uniaura.services.web.curso.<funcionalidade>.v1` | Feitas (substituem o antigo `/cursos`: CRUD, `CursoService`, `CursoRepository` e DTOs foram **apagados**). API só com GET/POST e `uid`. Paginação por `de`/`qtd`/`ordem` (`PageableQuery` da cloudsupport). Falta testar rodando. |
| `PesquisarDisciplinasDoCurso` | `erp.uniaura.services.web.disciplina.pesquisarDisciplinasDoCurso.v1` | Feita (substitui `GET /cursos/{id}/disciplinas`). |
| `BuscarCursoComponent` (`@Component`) | `erp.uniaura.services.common.curso` | Busca `Curso` por `uid` (funcionalidades novas) ou por `id` (uso **transitório** de DisciplinaService, TurmaService, ColegiadoService e PainelCoordenacaoService). |

| `PesquisarDisciplinas`, `DetalharDisciplina`, `CadastrarDisciplina`, `AlterarDisciplina`, `RemoverDisciplina` | `erp.uniaura.services.web.disciplina.<funcionalidade>.v1` | Feitas (substituem o antigo `/disciplinas`: `DisciplinaService`, repositórios, DTOs e controlador **apagados**). `buscarPorCodigo` virou o filtro `codigo` de `PesquisarDisciplinas`. Falta testar rodando. |
| `PesquisarPrerequisitosDaDisciplina`, `AdicionarPrerequisitoDaDisciplina`, `RemoverPrerequisitoDaDisciplina` | `erp.uniaura.services.web.disciplina.<funcionalidade>.v1` | Feitas (substituem `/disciplinas/{id}/prerequisitos`). |
| `BuscarDisciplinaComponent` (`@Component`) | `erp.uniaura.services.common.disciplina` | Busca `Disciplina` por `uid` ou, transitoriamente, por `id` (TurmaService). |
| `PesquisarTurmas`, `DetalharTurma`, `CadastrarTurma`, `AlterarTurma`, `RemoverTurma` | `erp.uniaura.services.web.turma.<funcionalidade>.v1` | Feitas (substituem o antigo `/turmas`: `TurmaService`, repositórios, DTOs e controlador **apagados**). `buscarPorCodigo` virou o filtro `codigo` de `PesquisarTurmas`. Falta testar rodando. |
| `PesquisarAlunosDaTurma`, `PesquisarDisciplinasDaTurma`, `AdicionarDisciplinaNaTurma`, `RemoverDisciplinaDaTurma` | `erp.uniaura.services.web.turma.<funcionalidade>.v1` | Feitas (substituem `/turmas/{id}/alunos` e `/turmas/{id}/disciplinas`). |
| `BuscarTurmaComponent`, `BuscarTurmaDisciplinaComponent`, `BuscarProfessorComponent` | `erp.uniaura.services.common.<turma|professor>` | Busca por `uid`/`id`. Os de turma e vínculo servem a Matrícula, Coordenação, Atividade, Avaliação, Frequência, Material e Nota (que ainda usam `id`); o de professor, a Turma (Professor ainda não migrou). `BuscarDisciplinaComponent.buscarPorId` foi removido (ninguém mais usa). |

Observação: `TurmaDisciplinaResponseDTO` foi movido para `modules/coordenacao/dto`, único módulo antigo que ainda o devolve (`AlocacaoDocenteService`).
| `PesquisarProfessores`, `DetalharProfessor`, `CadastrarProfessor`, `AlterarProfessor`, `RemoverProfessor` | `erp.uniaura.services.web.professor.<funcionalidade>.v1` | Feitas (substituem o antigo `/professores`: `ProfessorService`, repositórios, DTOs e controlador **apagados**). O retorno agora é plano (nome, e-mail etc. do usuário no mesmo nível). Falta testar rodando. |
| `PesquisarDisciplinasDoProfessor`, `AdicionarDisciplinaAoProfessor`, `RemoverDisciplinaDoProfessor` | `erp.uniaura.services.web.professor.<funcionalidade>.v1` | Feitas (substituem `/professores/{id}/disciplinas`). Usam `disciplinaUid`; internamente o vínculo guarda o `id` da disciplina (coluna `disciplinaId`). |
| `BuscarProfessorComponent` (+ `buscarPorUid` e `buscarPorUsuarioId`), `VerificarProfessorHabilitadoComponent` | `erp.uniaura.services.common.professor` | Servem a Turma (novas funcionalidades) e aos módulos antigos de Avaliação, Coordenação, Estágio, Frequência e TCC. |

Pendência de Professor: `CadastrarProfessor` ainda chama o `UsuarioService` antigo para criar o usuário (transitório, sai quando Usuário migrar).
| `PesquisarEventosAcademicos`, `PesquisarEventosPublicos`, `DetalharEventoAcademico`, `CadastrarEventoAcademico`, `AlterarEventoAcademico`, `RemoverEventoAcademico` | `erp.uniaura.services.web.calendario.<funcionalidade>.v1` | Feitas (substituem o antigo `/eventos-academicos`; módulo **apagado**). `PesquisarEventosPublicos` é público: `/uniaura/pesquisarEventosPublicos.v1` entrou em `cloudsupport.security.allow-paths` no lugar de `/eventos-academicos/publicos`. Componente: `BuscarEventoAcademicoComponent`. Falta testar rodando. |
| `PesquisarComunicados`, `PesquisarMural`, `CadastrarComunicado`, `AlterarComunicado`, `RemoverComunicado` | `erp.uniaura.services.web.comunicado.<funcionalidade>.v1` | Feitas (substituem o antigo `/comunicados` e `/comunicados/mural`; módulo **apagado**). A turma agora é `turmaUid` (o comunicado guarda o `id` por dentro). Falta testar rodando. |
| `BuscarComunicadoComponent`, `VerificarAutorizacaoDoComunicadoComponent`, `BuscarUsuarioComponent`, `ConsultarUidDasTurmasComponent` | `erp.uniaura.services.common.<comunicado|usuario|turma>` | `BuscarUsuarioComponent` (por `id` do token) já serve de base para a migração de Usuário. |
| `DetalharMaterial`, `PesquisarMateriaisDaTurmaDisciplina`, `CadastrarMaterial`, `AlterarMaterial`, `RemoverMaterial` | `erp.uniaura.services.web.material.<funcionalidade>.v1` | Feitas (substituem o antigo `/materiais`; módulo **apagado**). Cadastrar e alterar continuam em multipart (parte `dados` em JSON + parte `arquivo`). O vínculo é `turmaDisciplinaUid` (o `uid` devolvido por `PesquisarDisciplinasDaTurma`). Falta testar o upload rodando. |
| `BuscarMaterialComponent`, `VerificarAutorizacaoDoMaterialComponent`, `AplicarArquivoOuLinkDoMaterialComponent` | `erp.uniaura.services.common.material` | O último concentra a regra de arquivo/link e usa o `StorageService`. |
| `PesquisarFuncionarios`, `DetalharFuncionario`, `CadastrarFuncionario`, `AlterarFuncionario`, `RemoverFuncionario` | `erp.uniaura.services.web.funcionario.<funcionalidade>.v1` | Feitas (substituem o antigo `/funcionarios`; módulo **apagado**). Retorno plano (dados do usuário no mesmo nível). `CadastrarFuncionario` ainda chama o `UsuarioService` antigo (transitório). Componente: `BuscarFuncionarioComponent`. Falta testar rodando. |
| `PesquisarResponsaveis`, `DetalharResponsavel`, `CadastrarResponsavel`, `AlterarResponsavel`, `RemoverResponsavel`, `PesquisarAlunosDoResponsavel`, `VincularAlunoAoResponsavel`, `DesvincularAlunoDoResponsavel` | `erp.uniaura.services.web.responsavel.<funcionalidade>.v1` | Feitas (substituem o antigo `/responsaveis`). Identificam por `uid`. `CadastrarResponsavel` ainda chama o `UsuarioService` antigo (transitório, sai quando Usuário migrar). |
| `BuscarResponsavelComponent`, `BuscarVinculoComponent`, `BuscarAlunoComponent` (só `buscarPorUid`) | `erp.uniaura.services.common.<responsavel|aluno>` | `BuscarAlunoComponent` cresce quando Aluno migrar. |
| **NOVO** `CadastrarAcompanhamentoEspecial`, `PesquisarAcompanhamentosEspeciais`, `DetalharAcompanhamentoEspecial`, `AlterarAcompanhamentoEspecial`, `RegistrarAtendimentoDeAcompanhamento`, `PesquisarRegistrosDeAcompanhamento` | `erp.uniaura.services.web.acompanhamento.<funcionalidade>.v1` | Módulo criado já no padrão Cloudsupport (alunos especiais acompanhados pelo departamento de acolhimento). Entidades `AcompanhamentoEspecial` e `RegistroDeAcompanhamento`; enums `TipoNecessidadeEspecial`, `StatusAcompanhamento`, `TipoRegistroAcompanhamento` (com conversores). Acesso só ADMIN e COORDENADOR (dado sensível, LGPD) até existir um cargo próprio do departamento. Rodar `docs/sql/acompanhamento-uid.sql` depois da primeira subida. |
| `BuscarAcompanhamentoEspecialComponent` | `erp.uniaura.services.common.acompanhamento` | Reaproveitado por 4 funcionalidades. |

| `RegistrarManifestacao`, `PesquisarMinhasManifestacoes`, `PesquisarManifestacoes`, `DetalharManifestacao`, `ResponderManifestacao` | `erp.uniaura.services.web.ouvidoria.<funcionalidade>.v1` | Feitas (substituem o antigo `/ouvidoria/manifestacoes`). `DetalharManifestacao` aceita `uid` ou `protocolo`. Sem consumidor no frontend. |
| `BuscarManifestacaoComponent` | `erp.uniaura.services.common.ouvidoria` | `buscarPorUid` e `buscarPorProtocolo`. |

| `EmitirDeclaracaoDeMatricula`, `EmitirHistoricoEscolar`, `EmitirCertificadoDeConclusao`, `PesquisarCertificadosEmitidos`, `VerificarCertificado` | `erp.uniaura.services.web.certificado.<funcionalidade>.v1` | Feitas (substituem o antigo `/certificados`). As 3 emissões respondem o PDF como anexo (`ResponseEntity<byte[]>`); **confirmar com o responsável pela arquitetura se o manual aceita resposta binária num `@Ws`** (o manual só restringe a entrada a GET/POST). O aluno passou a só emitir o próprio documento (antes qualquer `ROLE_ALUNO` emitia de qualquer aluno). `VerificarCertificado` é público: `/certificados/verificar` saiu do `allow-paths` e entrou `/uniaura/verificarCertificado.v1`. `EmitirHistoricoEscolar` lê as notas pelo `ListarNotasDoAlunoComponent` (a pendência de Nota foi resolvida). |
| `BuscarAlunoParaEmissaoComponent`, `RegistrarEmissaoDeCertificadoComponent`, `MontarPdfDeCertificadoComponent` | `erp.uniaura.services.common.certificado` | Reaproveitados pelas 3 emissões. |

| `AbrirProcesso`, `PesquisarMeusProcessos`, `PesquisarProcessos`, `DetalharProcesso`, `TramitarProcesso`, `CancelarProcesso` | `erp.uniaura.services.web.processo.<funcionalidade>.v1` | Feitas (substituem o antigo `/processos`). `DetalharProcesso` aceita `uid` ou `protocolo`. Sem consumidor no frontend. O `PainelCoordenacao` antigo continua lendo a entidade `Processo` por JPQL. |
| `BuscarProcessoComponent`, `RegistrarMovimentacaoDoProcessoComponent`, `ValidarTransicaoDoProcessoComponent` | `erp.uniaura.services.common.processo` | Reaproveitados por Abrir, Tramitar e Cancelar. `BuscarAlunoComponent` ganhou `buscarPorUsuarioId`. |

| `EnviarDocumentoDoAluno`, `PesquisarMeusDocumentos`, `PesquisarDocumentosDoAluno`, `AnalisarDocumento`, `RemoverDocumento` | `erp.uniaura.services.web.documento.<funcionalidade>.v1` | Feitas (substituem o antigo `/documentos`). O histórico de versões virou o filtro `tipo` + ordem `-versao` de `PesquisarDocumentosDoAluno`. `EnviarDocumentoDoAluno` é multipart (`dados` + `arquivo`). Sem consumidor no frontend. |
| `BuscarDocumentoDoAlunoComponent` | `erp.uniaura.services.common.documento` | Reaproveitado por Analisar e Remover. |

Pendência de Documento: o `LgpdService` antigo chama `PesquisarMeusDocumentosServiceV1` e o `DadosPessoaisResponseDTO` carrega `PesquisarMeusDocumentosDocumentoRetornoV1` (transitório, sai quando o LGPD migrar).

| `CadastrarAtividade`, `AlterarAtividade`, `RemoverAtividade`, `DetalharAtividade`, `PesquisarAtividadesDaTurmaDisciplina` | `erp.uniaura.services.web.atividade.<funcionalidade>.v1` | Feitas (substituem o antigo `/atividades`). Identificam o vínculo turma/disciplina por `turmaDisciplinaUid`. Sem consumidor no frontend. |
| `EntregarAtividade`, `AvaliarEntrega`, `PesquisarEntregasDaAtividade`, `PesquisarEntregasDoAluno` | `erp.uniaura.services.web.atividade.<funcionalidade>.v1` | Feitas. `EntregarAtividade` é multipart (`dados` + `arquivo`). Endurecimentos: o professor só lista as entregas das próprias atividades; o aluno só lê as próprias entregas e o responsável só as dos alunos vinculados (antes qualquer `ROLE_ALUNO`/`ROLE_RESPONSAVEL` lia de qualquer aluno). A checagem de matrícula ATIVA virou JPQL em `Matricula` (o `MatriculaRepository` antigo não é mais usado por Atividade). |
| `BuscarAtividadeComponent`, `VerificarAutorizacaoDaAtividadeComponent`, `VerificarProfessorDaDisciplinaComponent` | `erp.uniaura.services.common.<atividade|professor>` | O último saiu do componente de Material (agora compartilhado por Material e Atividade). `BuscarResponsavelComponent` ganhou `buscarPorUsuarioId`. |

| `RegistrarAvaliacaoDocente`, `ConsultarMediaDaTurmaDisciplina`, `ConsultarMediaDoProfessor`, `ConsultarMeuDesempenho`, `PesquisarComentariosDaTurmaDisciplina` | `erp.uniaura.services.web.avaliacaoDocente.<funcionalidade>.v1` | Feitas (substituem o antigo `/avaliacoes-docente`). Comentários continuam anônimos e agora são paginados. Sem consumidor no frontend. |
| `ResumirAvaliacoesDocenteComponent` (+ `ResumoDeAvaliacaoDocente`, um `record`) | `erp.uniaura.services.common.avaliacaoDocente` | Reaproveitado pelas 3 consultas de média. |

| `CadastrarAula`, `PesquisarFrequenciasDaAula`, `RegistrarChamada`, `ConsultarPercentualDePresenca`, `ConsultarResumoDeFrequenciaDoAluno` | `erp.uniaura.services.web.frequencia.<funcionalidade>.v1` | Feitas (substituem o antigo `/aulas` e `/alunos/{id}/frequencia`). `CadastrarAula` e `RegistrarChamada` agora exigem ser o professor da disciplina (ou coordenação/admin); antes qualquer professor mexia em qualquer turma. A chamada identifica o aluno por `alunoUid`; a disciplina, por `disciplinaUid`. Sem consumidor no frontend. |
| `BuscarAulaComponent`, `CalcularPercentualDePresencaComponent` (+ `PercentualDePresenca`, um `record`) | `erp.uniaura.services.common.frequencia` | O cálculo é reaproveitado pelas duas consultas de percentual. |

| `LancarNota`, `AlterarNota`, `RemoverNota`, `PesquisarNotasDaTurmaDisciplina`, `PesquisarNotasDoAluno`, `ConsultarBoletim` | `erp.uniaura.services.web.nota.<funcionalidade>.v1` | Feitas (substituem o antigo `/notas`, `/turmas/{id}/disciplinas/{id}/notas` e `/alunos/{id}/boletim`). Identificam por `uid`; o boletim recebe `periodoLetivo`. O e-mail de aviso ao aluno continua no `LancarNota`. Sem consumidor no frontend. |
| `BuscarNotaComponent`, `VerificarPermissaoDeLancamentoDeNotaComponent`, `ListarNotasDoAlunoComponent` | `erp.uniaura.services.common.nota` | O último serve ao histórico escolar do Certificado. |

Pendência de Nota: o `LgpdService` antigo chama `PesquisarNotasDoAlunoServiceV1` e o `DadosPessoaisResponseDTO` carrega `PesquisarNotasDoAlunoNotaRetornoV1` (transitório, sai quando o LGPD migrar).

| `MatricularAluno`, `TrancarMatricula`, `CancelarMatricula`, `DetalharMatricula`, `PesquisarMatriculas` | `erp.uniaura.services.web.matricula.<funcionalidade>.v1` | Feitas (substituem o antigo `/matriculas`). O histórico do aluno virou o filtro `alunoUid` de `PesquisarMatriculas`. Identificam aluno, turma e matrícula por `uid`. Sem consumidor no frontend. A turma atual do aluno continua guardada por `id` em `Aluno.turmaAtualId` (sai quando Aluno migrar). |
| `BuscarMatriculaComponent`, `AlterarStatusDaMatriculaComponent` | `erp.uniaura.services.common.matricula` | O segundo é reaproveitado por Trancar e Cancelar. |

| `AbrirEstagio`, `PesquisarEstagios`, `DetalharEstagio`, `ConsultarMeuEstagio`, `EncerrarEstagio` | `erp.uniaura.services.web.estagio.<funcionalidade>.v1` | Feitas (substituem o antigo `/estagios`). Aluno e professor orientador por `uid`. Endurecimentos: `AbrirEstagio` recusa fim previsto anterior ao início; `EncerrarEstagio` recusa o status `EM_ANDAMENTO`. |
| `EnviarRelatorioDeEstagio`, `PesquisarRelatoriosDoEstagio`, `AnalisarRelatorioDeEstagio` | `erp.uniaura.services.web.estagio.<funcionalidade>.v1` | Feitas (substituem `/estagios/{id}/relatorios`). `EnviarRelatorioDeEstagio` é multipart (`dados` + `comprovante` opcional). Endurecimentos: listar relatórios passou a exigir ser o aluno dono ou equipe acadêmica (antes qualquer autenticado lia); analisar recusa o resultado `PENDENTE`. Sem consumidor no frontend. |
| `BuscarEstagioComponent`, `VerificarAcessoAoEstagioComponent` | `erp.uniaura.services.common.estagio` | O segundo é reaproveitado por enviar e listar relatórios. |

| `AbrirTcc`, `PesquisarTccs`, `DetalharTcc`, `ConsultarMeuTcc`, `AgendarDefesaDoTcc`, `FinalizarTcc` | `erp.uniaura.services.web.tcc.<funcionalidade>.v1` | Feitas (substituem o antigo `/tccs`). Aluno e professores por `uid`. Os filtros de `PesquisarTccs` (aluno, orientador, status) agora se combinam (antes só valia o primeiro informado). |
| `EnviarEntregaDeTcc`, `PesquisarEntregasDoTcc`, `AnalisarEntregaDeTcc` | `erp.uniaura.services.web.tcc.<funcionalidade>.v1` | Feitas (substituem `/tccs/{id}/entregas`). `EnviarEntregaDeTcc` é multipart (`dados` + `arquivo`). Endurecimentos: listar entregas exige ser o aluno dono, professor, coordenação ou admin (antes qualquer autenticado lia); analisar exige que o professor seja orientador ou coorientador do TCC e recusa o resultado `PENDENTE`. |
| `ConvocarMembroDaBanca`, `PesquisarMembrosDaBanca`, `LancarNotaDaBanca` | `erp.uniaura.services.web.tcc.<funcionalidade>.v1` | Feitas (substituem `/tccs/{id}/banca`). Endurecimento: o professor só lança a própria nota (antes qualquer professor lançava a de qualquer membro); coordenação e admin continuam lançando a de qualquer um. `PesquisarMembrosDaBanca` ordena por `id` (ordem de convocação). Sem consumidor no frontend. |
| `BuscarTccComponent`, `VerificarAcessoAoTccComponent` | `erp.uniaura.services.common.tcc` | O segundo é reaproveitado por enviar e listar entregas. |

| Biblioteca — Livro: `PesquisarLivros`, `DetalharLivro`, `CadastrarLivro`, `AlterarLivro`, `RemoverLivro`; Exemplar: `PesquisarExemplaresDoLivro`, `DetalharExemplar` (uid ou código de barras), `GerarCodigoDeBarras`, `CadastrarExemplar`, `AlterarExemplar`, `RemoverExemplar` | `erp.uniaura.services.web.biblioteca.<funcionalidade>.v1` | Feitas (substituem `/biblioteca/livros` e `/biblioteca/exemplares`). `CadastrarLivro`/`AlterarLivro` são multipart (`dados` + `capa`). |
| Biblioteca — Empréstimo: `RegistrarEmprestimo`, `DevolverEmprestimo`, `RenovarEmprestimo`, `DetalharEmprestimo`, `PesquisarEmprestimosDoUsuario` (equipe), `PesquisarMeusEmprestimos`; Multa: `PesquisarMultas`, `PesquisarMinhasMultasPendentes`, `PagarMulta`, `CancelarMulta`; Reserva: `ReservarLivro`, `CancelarReserva`, `PesquisarFilaDeReservasDoLivro`, `PesquisarMinhasReservas`; Configuração: `ConsultarConfiguracaoDaBiblioteca`, `AlterarConfiguracaoDaBiblioteca` | `erp.uniaura.services.web.biblioteca.<funcionalidade>.v1` | Feitas (substituem `/biblioteca/emprestimos`, `/multas`, `/reservas` e `/configuracoes`). A equipe identifica o usuário por `usuarioUid` (a tela pede o UID); o usuário logado usa os endpoints "Meus/Minhas", sem id. `RegistrarEmprestimo` aceita `codigoBarras` direto (a tela deixou de buscar o exemplar antes). `DevolverEmprestimo`/`RenovarEmprestimo` não devolvem o detalhe; a tela chama `DetalharEmprestimo`. **Endurecimentos:** empréstimos, reservas e multas de outro usuário deixaram de ser legíveis por qualquer autenticado; renovar e detalhar empréstimo exigem ser o dono ou a equipe da biblioteca (BIBLIOTECARIO/ADMIN); cancelar reserva idem. O `MultaGeradaEvent` foi removido (ninguém o escutava). |
| `BuscarLivroComponent`, `BuscarExemplarComponent`, `BuscarEmprestimoComponent`, `BuscarReservaComponent`, `BuscarMultaComponent`, `ObterConfiguracaoDaBibliotecaComponent` (com cache), `CalcularDiasDeAtrasoComponent`, `CalcularPrazoDoEmprestimoComponent`, `ReordenarFilaDeReservasComponent`, `VerificarAcessoAoEmprestimoComponent`, `VerificarMultaPendenteDoUsuarioComponent`, `BuscarValorDaMultaDoEmprestimoComponent`, `ContarExemplaresDoLivroComponent`, `GerarCodigoDeBarrasComponent`, `MarcarEmprestimosVencidosComponent`, `ExpirarReservasNaoRetiradasComponent` | `erp.uniaura.services.common.biblioteca` | `BuscarUsuarioComponent` ganhou `buscarPorUid`. |
| Jobs da Biblioteca: `MarcarEmprestimosVencidos` (3h), `ExpirarReservasNaoRetiradas` (3h30) | `erp.uniaura.services.jobs.<nome>` | Movidos de `modules/biblioteca/scheduler`; a regra vive nos componentes acima. A troca para repositório JDBC da Fase 5 continua pendente. |
| Financeiro — `PesquisarMensalidades`, `PesquisarMinhasMensalidades`, `DetalharMensalidade`, `CadastrarMensalidade`, `AlterarMensalidade`, `RemoverMensalidade`, `RegistrarPagamentoDaMensalidade`, `GerarPixDaMensalidade`, `GerarBoletoDaMensalidade` | `erp.uniaura.services.web.financeiro.<funcionalidade>.v1` | Feitas (substituem `/mensalidades`; o frontend não consumia). Pagamento fica dentro do retorno da mensalidade (campos `formaPagamento`, `valorPago`, `valorMulta`, `dataPagamento`). |
| Job do Financeiro: `MarcarMensalidadesAtrasadas` (4h) | `erp.uniaura.services.jobs.marcarMensalidadesAtrasadas` | Regra no `MarcarMensalidadesAtrasadasComponent`; envia e-mail ao aluno. |

| Coordenação — Painel: `ConsultarPainelDaCoordenacao`; Alocação: `AlocarProfessorNaTurmaDisciplina`, `DefinirProfessorRegenteDaTurma`, `ConsultarCargaHorariaDoProfessor`; Plano de ensino: `CadastrarPlanoDeEnsino`, `AlterarPlanoDeEnsino`, `SubmeterPlanoDeEnsino`, `AvaliarPlanoDeEnsino`, `PesquisarPlanosDeEnsino`, `PesquisarMeusPlanosDeEnsino`, `DetalharPlanoDeEnsino` (por `uid` ou `turmaDisciplinaUid`); Colegiado: `AgendarReuniaoDoColegiado`, `AlterarReuniaoDoColegiado`, `ConvocarParticipanteDaReuniao`, `RemoverParticipanteDaReuniao`, `RegistrarAtaDaReuniao`, `CancelarReuniaoDoColegiado`, `PesquisarReunioesDoColegiado`, `DetalharReuniaoDoColegiado` | `erp.uniaura.services.web.coordenacao.<funcionalidade>.v1` | Feitas. Antes só o painel (`/painel-coordenacao`) tinha endpoint; alocação, plano e colegiado eram serviços sem controlador, e agora ganham endpoints (`ROLE_COORDENADOR`/`ROLE_ADMIN`; o professor cadastra, altera, submete e detalha o próprio plano). O frontend não consumia nenhum deles. |

| Auditoria — `PesquisarLogsDeAuditoria` (ADMIN); componente `RegistrarAuditoriaComponent` | `erp.uniaura.services.web.auditoria.pesquisarLogsDeAuditoria.v1`, `services/common/auditoria` | Feitas. O componente substitui o `AuditoriaService` antigo em Aluno, LGPD, Pré-matrícula e Financeiro (continua best effort). Filtro por entidade tem prioridade sobre usuário; usuário vira `usuarioUid`. |
| Relatório — `GerarRelatorioFinanceiro`, `GerarRelatorioDaBiblioteca` | `erp.uniaura.services.web.relatorio.<funcionalidade>.v1` | Feitas (substituem `/relatorios/financeiro` e `/relatorios/biblioteca`); consultas JPQL direto no serviço, sem repositórios. O frontend não consumia. |

| Usuário — `PesquisarUsuarios`, `DetalharUsuario`, `CadastrarUsuario`, `AlterarUsuario`, `RemoverUsuario`; componentes `CriarUsuarioComponent`, `ConsultarUsuarioComponent` | `erp.uniaura.services.web.usuario.<funcionalidade>.v1` | Feitas. `CadastrarUsuario` também substitui o antigo `/auth/register`. Os cadastros de Aluno, Professor, Funcionário, Responsável, Pré-matrícula e o `AdminSeeder` criam o usuário pelo `CriarUsuarioComponent`. `UsuarioAutenticadoProvider` e `UsuarioDetailsService` (infra do login) usam o `ConsultarUsuarioComponent`. |
| Autenticação — `AutenticarUsuario`, `RenovarToken`, `AlterarMinhaSenha`, `ConsultarUsuarioLogado`, `SolicitarRecuperacaoDeSenha`, `RedefinirSenha` | `erp.uniaura.services.web.autenticacao.<funcionalidade>.v1` | Feitas, mantendo a exceção D1 (login e JWT próprios; `TokenService`, `LoginRateLimiter` e `SecurityConfig` ficam). Públicas por `allow-paths`: `autenticarUsuario`, `renovarToken`, `solicitarRecuperacaoDeSenha`, `redefinirSenha` e `realizarPreMatricula`. O login devolve `usuario` (com `uid`) no lugar de `usuarioDTO` (com `id`). Os pedidos de recuperação continuam em memória (`GuardarPedidosDeRecuperacaoComponent`). |
| Aluno — `PesquisarAlunos`, `DetalharAluno` (por `uid` ou `matriculaRA`), `CadastrarAluno`, `AlterarAluno`, `RemoverAluno`, `ConsultarMeuPerfilDeAluno`, `AlterarMeuPerfilDeAluno`; Pré-matrícula: `RealizarPreMatricula` | `erp.uniaura.services.web.aluno.<funcionalidade>.v1`, `services.web.preMatricula.realizarPreMatricula.v1` | Feitas. A turma do aluno passa a ser informada e devolvida por `turmaAtualUid` (o campo `turmaAtualId` da entidade continua `id`, tarefa da Fase 4). Os dois endpoints do perfil trocaram GET/PUT por GET/POST. |
| LGPD — `ExportarMeusDadosPessoais` | `erp.uniaura.services.web.lgpd.exportarMeusDadosPessoais.v1` | Feita; chama os serviços de perfil, mensalidades, notas e documentos. |

**Fase 4, item 6 (erros) — feito:** o pacote `erp.uniaura.exception` (`GlobalExceptionHandler`, `BusinessException`, `ResourceNotFoundException`, `ValidationException`, `ErrorResponse`) foi apagado e `LoginRateLimiter`, `TokenService` e `LocalStorageService` usam a `BusinessException` da cloudsupport. Efeitos: regra de negócio e login inválido passam de 400 para **422**; `@Valid` passa de 400 para **422** com a lista `fieldMessages` (o `lib/api.js` agora mostra essas mensagens); `IllegalArgumentException` passa de 400 para 500 com `tracking`. Isso fixa a divergência do manual em 422 para `@Valid`; confirmar com o dono da arquitetura.

Frontend desta rodada: `lib/auth.js`, `RecuperarSenha.jsx`, `MenuPerfilAluno.jsx` e `app/portal-do-aluno/cadastro/page.js` passaram a usar `app/remotes/uniaura/{autenticacao,aluno,preMatricula}/*Remote.js`; `lib/api/alunos.js` usa o adapter `criarApiDeCadastro`. Pastas `dto/auth` e `modules` do backend foram apagadas.

Mudanças de comportamento da Coordenação: o painel recebe `cursoUid` (antes `curso` por `id`) e as consultas viraram JPQL montado no serviço, sem o repositório antigo; `DetalharPlanoDeEnsino` exige que o professor logado seja o responsável (antes sem checagem); `RegistrarAtaDaReuniao` recebe presenças por `usuarioUid`.

Mudanças de comportamento do Financeiro: pix, boleto e detalhe passaram a exigir que a mensalidade seja do aluno logado (ou perfil da equipe financeira); antes qualquer usuário autenticado gerava cobrança de qualquer mensalidade. `RegistrarPagamento` continua só para o dono ou a equipe. A auditoria do pagamento ainda usa o `AuditoriaService` antigo (transitório, até Auditoria migrar). `LgpdService` e `RelatorioFinanceiroService` foram religados (LGPD chama `PesquisarMinhasMensalidadesServiceV1`; o relatório consulta `Mensalidade` pelo `EntityManager`, transitório).

Pendência de Biblioteca: o `RelatorioBibliotecaService` antigo passou a consultar `Exemplar` e `Multa` direto pelo `EntityManager` (transitório, vira funcionalidade quando Relatório migrar). No frontend, as 11 telas passaram a usar `app/remotes/uniaura/biblioteca/*Remote.js` e `lib/api/biblioteca.js` foi removido.

**Frontend dos cadastros da secretaria (Funcionário, Professor, Responsável):** as páginas `app/secretaria/{funcionarios,professores,responsaveis}` ficaram sem backend quando esses módulos migraram, porque a checagem de consumidores não olhou `lib/api/*`. Corrigido: `lib/api/cadastroUniaura.js` monta a API de cada cadastro sobre as funcionalidades novas (paginação `page/size/sort` → `de/qtd/ordem`, `uid` como `id`), e `CrudEntityPage` passou a buscar o cadastro completo antes de editar (a lista só traz o resumo e salvar apagaria CPF, telefone e nascimento). Regra daqui em diante: antes de apagar um módulo antigo, procurar o caminho em `frontend/src/lib/api/` além de `app/`.

**Entidades (manual, capítulo Persistência):** o manual só cita `@Getter`/`@Setter` nas entidades e manda manter os relacionamentos como `@ManyToOne(fetch = LAZY)`; por isso `@ManyToOne`/`@JoinColumn` ficam. `@Builder`, `@AllArgsConstructor` e `@NoArgsConstructor` saem de cada entidade assim que o último módulo antigo que a monta com `Entidade.builder()` migrar (já saíram das de Curso, Disciplina, Turma, Professor, Funcionário, Responsável, Comunicado, Material, Calendário, Biblioteca-configuração, Manifestação e do acompanhamento). Desvios do manual ainda abertos: chave estrangeira nomeada `alunoId` (manual: `aluno_id`), datas em `LocalDateTime` (manual: `OffsetDateTime`) e `@Column(unique = true)` do `uid` ausente. Os três mexem no banco e ficam para a Fase 4 (Liquibase).

**Exceções transitórias (remover quando cada módulo migrar):** os retornos de curso e de disciplina trazem também o `id` sequencial, porque Disciplina, Turma e Professor ainda recebem `cursoId`/`disciplinaId` por `id`. Ao migrar esses módulos, passam a usar `uid` e o campo `id` sai dos retornos.
Notas do piloto: o `@Ws` sem path tira a URL do pacote (`services.web.<grupo>.<funcionalidade>.v1`) e do nome da classe (`...WsV1`); a permissão ainda é `ROLE_ALUNO` (Fase 6 troca); o cálculo do semestre atual está duplicado com o `AlunoPerfilService` e vira `@Component` quando a segunda funcionalidade precisar dele.
### Ordem sugerida dos módulos
1. **Piloto (somente leitura):** Curso e Disciplina — inclui a nova `PesquisarDisciplinasDoAluno` (substitui o `cursos.json` lido no navegador).
2. **Autenticação:** login, refresh, alterar senha, recuperar senha (depende da D1).
3. **Aluno** (perfil, pré-matrícula) e **Usuário**.
4. **Acadêmico:** Turma, Matrícula, Nota, Frequência, Calendário, Comunicado.
5. **Biblioteca** (Livro, Exemplar, Empréstimo, Reserva, Multa, Configuração).
6. **Financeiro, Processo, Ouvidoria, Documento, Certificado.**
7. **Coordenação, TCC, Estágio, Avaliação, Relatórios, Auditoria, LGPD, Professor, Funcionário, Responsável.**

Ao terminar um módulo: apagar repositórios, services por entidade e controladores antigos dele.

## Fase 4 — Webservices (`@Ws`)

Para cada funcionalidade:
1. Classe `<Funcionalidade>WsV1 extends BaseWs`, anotada `@Ws`, **sem path**; o endpoint sai do nome: `/{prefixo}/nomeFuncionalidade.v1`.
2. Só `@GetMapping` (consulta) ou `@PostMapping` (altera dados); corpo em JSON com `@Valid @RequestBody`. Nada de PUT/DELETE/`@PathVariable`.
3. `@Operation(summary = ...)` obrigatório, com resumo das regras e descrição dos parâmetros; remover `@Tag`.
4. Nunca receber ou devolver o `id` sequencial: usar `uid`.
5. Segurança por anotação: `@IsAuthenticated`, `@HasAuthority`, `@HasAnyAuthority`, `@LoggedIn*`.
6. Ao fim da migração, remover o `GlobalExceptionHandler` próprio e o `BusinessException` do projeto; adotar os códigos do manual (200, 202, 400, 401, 403, 404, 422, 500) e `fieldMessages`/`tracking`.
   - Atenção: o manual diverge sobre o código das violações de `@Valid` (400 em um lugar, 422 em outro). Confirmar com o dono da arquitetura e fixar um.
7. Versionamento: mudança não retrocompatível clona todas as classes para `V2`; versões não se referenciam.

## Fase 5 — Rotinas (`jobs`)

1. Mover `MensalidadeAtrasadaJob`, `EmprestimoVencidoJob`, `ReservaExpiradaJob` e seus `Schedule` para `services.jobs.<assunto>.<funcionalidade>`.
2. Job só orquestra e chama o `@Service`; sem regra de negócio nem transação no Job.
3. Criar as tabelas `jobinstance`, `jobexecution`, `jobparam`, `jobconfigurations` (script Liquibase) e usar `cloudsupport.jobs.repository=jdbc`.
4. Habilitar agendamento em **uma só** instância (`cloudsupport.jobs.scheduling.enabled`).

## Fase 6 — Segurança

1. Trocar `ROLE_ALUNO`, `ROLE_ADMIN` etc. pelo formato `uniaura:grupo:privilegio` (ex.: `uniaura:aluno:perfil`, `uniaura:biblioteca:emprestar`). Papéis (RBAC) mapeiam para essas permissões.
2. O token passa a levar essas permissões na claim `roles`.
3. Definir `cloudsupport.auditing.auditor-jwt-claim` (por exemplo `user_id`).
4. Decisão D1 (login próprio mantido): documentar a exceção no README; não remover o `TokenService`.
5. Conferir `allow-paths`, CORS e a lista de rotas públicas.

## Fase 7 — Configuração e observabilidade

1. Alinhar `application.properties` ao que a biblioteca já define (remover duplicados, manter só diferenças justificadas).
2. Logging com Log4j2 e `build-info.properties`; Micrometer/Prometheus; Actuator com probes.
3. Segredos fora do repositório (variáveis de ambiente ou `application-local.properties`, já fora do Git).

## Fase 8 — Frontend

1. Criar a camada `app/remotes/<microsservico>/<grupo>/<funcionalidade>Remote.js`: uma função por endpoint, com `Networking.fetch`, `validateResponse` e exportação nomeada. Trocar as 60 chamadas diretas a `api()` por essas funções.
2. Estrutura de páginas: `app/(pages)/(client)/(protected|public)/...`, com `layout.js` raiz, `error.js` e `AuthRequired` no layout protegido.
3. Ambiente: `public/profile.json` e `useProfile()` no lugar de `BASE_API` fixo.
4. Trocar `notificar` por `useToast`, o guarda do `AppShell` por `AuthRequired`/`useAuth`, e o indicador de processamento por `useProcessing`.
5. Código: `async/await` → Promises; `||` → `??` quando for valor padrão; `export default` só onde o Next exigir; nomes de pasta/arquivo em camelCase (ver D4).
6. Dividir componentes com mais de 200 linhas (`cadastro` tem 1.531, `matriculas` 554, `SiteHeader` 505, `home` 481, `contato` 468).
7. Formulários com React Hook Form.
8. Remover os dados fixos (`dados.js`, `cursos.json` no navegador) à medida que o backend ganhar as funcionalidades.

## Fase 9 — Git, testes e documentação

1. Ramos `devNN` por demanda, merge `--no-ff`, sem rebase/squash, tag por entrega (`dev77.1`, `master.1`).
2. Cobertura mínima: um teste por funcionalidade (service) e um por endpoint crítico.
3. Atualizar o README com a estrutura de pacotes final e esta lista de decisões.

---

## Definição de pronto da migração (100%)

- [ ] Nenhum `JpaRepository`, `Page`/`Pageable`, DAO ou service por entidade no backend.
- [ ] Todo endpoint é `GET`/`POST` com nome `nomeFuncionalidade.vN`, `@Operation` e `uid`.
- [ ] Nenhum DTO com sufixo `DTO`; todos `Params`/`Retorno` + versão.
- [ ] Entidades em `persistence.entities`, com `uid`, `OffsetDateTime`, `Set`, sem cascade/OneToOne/ManyToMany.
- [ ] `BusinessException` e tratamento de erro da cloudsupport.
- [ ] Jobs em `services.jobs`, com repositório JDBC.
- [ ] Permissões `uniaura:grupo:privilegio` e decisão D1 aplicada.
- [ ] Frontend com `remotes`, `profile.json`, hooks da cloudsupport e sem dados fixos.
- [ ] Banco só por Liquibase; testes por funcionalidade; Git no fluxo do manual.
