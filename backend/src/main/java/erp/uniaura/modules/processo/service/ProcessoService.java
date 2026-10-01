package erp.uniaura.modules.processo.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.protocolo.GeradorProtocolo;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.processo.dto.MovimentacaoProcessoResponseDTO;
import erp.uniaura.modules.processo.dto.ProcessoRequestDTO;
import erp.uniaura.modules.processo.dto.ProcessoResponseDTO;
import erp.uniaura.modules.processo.dto.TramitarProcessoRequestDTO;
import erp.uniaura.modules.processo.model.MovimentacaoProcesso;
import erp.uniaura.modules.processo.model.Processo;
import erp.uniaura.modules.processo.model.StatusProcesso;
import erp.uniaura.modules.processo.model.TipoProcesso;
import erp.uniaura.modules.processo.repository.MovimentacaoProcessoRepository;
import erp.uniaura.modules.processo.repository.ProcessoRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProcessoService {

    private static final String PREFIXO_PROTOCOLO = "PRC";

    private static final int MAX_TENTATIVAS_PROTOCOLO = 5;

    // --- TRANSIÇÕES PERMITIDAS PARA CADA STATUS ---
    private static final Map<StatusProcesso, Set<StatusProcesso>> TRANSICOES = new EnumMap<>(Map.of(
            StatusProcesso.ABERTO,
            Set.of(StatusProcesso.EM_ANALISE, StatusProcesso.AGUARDANDO_ALUNO,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO),
            StatusProcesso.EM_ANALISE,
            Set.of(StatusProcesso.AGUARDANDO_ALUNO,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO),
            StatusProcesso.AGUARDANDO_ALUNO,
            Set.of(StatusProcesso.EM_ANALISE,
                    StatusProcesso.DEFERIDO, StatusProcesso.INDEFERIDO, StatusProcesso.CANCELADO)
    ));

    private final ProcessoRepository processoRepository;
    private final MovimentacaoProcessoRepository movimentacaoProcessoRepository;
    private final AlunoRepository alunoRepository;
    private final GeradorProtocolo geradorProtocolo;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- O ALUNO AUTENTICADO ABRE UM NOVO REQUERIMENTO ---
    @Transactional
    public ProcessoResponseDTO abrir(ProcessoRequestDTO dto) {
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        Aluno aluno = alunoDoUsuarioOuFalha(usuarioAutenticado);

        Processo processo = Processo.builder()
                .protocolo(gerarProtocoloUnico())
                .aluno(aluno)
                .tipo(dto.getTipo())
                .assunto(dto.getAssunto())
                .descricao(dto.getDescricao())
                .status(StatusProcesso.ABERTO)
                .build();

        processoRepository.save(processo);

        registrarMovimentacao(processo, usuarioAutenticado, null, StatusProcesso.ABERTO,
                "Processo aberto pelo aluno.", true);

        return toResponse(processo, List.of());
    }

    // --- LISTA OS PROCESSOS DO ALUNO AUTENTICADO ---
    @Transactional(readOnly = true)
    public Page<ProcessoResponseDTO> listarMeusProcessos(StatusProcesso status, Pageable pageable) {
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        Aluno aluno = alunoDoUsuarioOuFalha(usuarioAutenticado);

        Page<Processo> pagina = status == null
                ? processoRepository.findByAlunoId(aluno.getId(), pageable)
                : processoRepository.findByAlunoIdAndStatus(aluno.getId(), status, pageable);

        return pagina.map(p -> toResponse(p, null));
    }

    // --- LISTAGEM ADMINISTRATIVA COM FILTROS OPCIONAIS ---
    @Transactional(readOnly = true)
    public Page<ProcessoResponseDTO> listar(StatusProcesso status, TipoProcesso tipo, Pageable pageable) {
        return processoRepository.buscarComFiltros(status, tipo, pageable).map(p -> toResponse(p, null));
    }

    // --- BUSCA UM PROCESSO E A SUA LINHA DO TEMPO ---
    @Transactional(readOnly = true)
    public ProcessoResponseDTO buscarPorId(Long id) {
        Processo processo = buscarEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        boolean somenteVisiveis = validarPodeVisualizar(processo, usuarioAutenticado);

        return toResponse(processo, buscarMovimentacoes(processo.getId(), somenteVisiveis));
    }

    // --- BUSCA UM PROCESSO PELO NÚMERO DE PROTOCOLO ---
    @Transactional(readOnly = true)
    public ProcessoResponseDTO buscarPorProtocolo(String protocolo) {
        Processo processo = processoRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new ResourceNotFoundException("Processo", protocolo));
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        boolean somenteVisiveis = validarPodeVisualizar(processo, usuarioAutenticado);

        return toResponse(processo, buscarMovimentacoes(processo.getId(), somenteVisiveis));
    }

    // --- A SECRETARIA MOVIMENTA O PROCESSO PARA UM NOVO STATUS ---
    @Transactional
    public ProcessoResponseDTO tramitar(Long id, TramitarProcessoRequestDTO dto) {
        Processo processo = buscarEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();

        StatusProcesso statusProcessoAnterior = processo.getStatus();
        validarTransicao(statusProcessoAnterior, dto.getStatus());

        boolean encerrando = dto.getStatus() == StatusProcesso.DEFERIDO || dto.getStatus() == StatusProcesso.INDEFERIDO;

        if (encerrando && (dto.getComentario() == null || dto.getComentario().isBlank())) {
            throw new BusinessException("É obrigatório registrar o parecer ao deferir ou indeferir um processo.");
        }

        processo.setStatus(dto.getStatus());
        processo.setResponsavel(usuarioAutenticado);

        if (dto.getPrazoResposta() != null) {
            processo.setPrazoResposta(dto.getPrazoResposta());
        }

        if (encerrando) {
            processo.setParecerFinal(dto.getComentario());
            processo.setEncerradoEm(LocalDateTime.now());
        }

        processoRepository.save(processo);

        boolean visivel = dto.getVisivelParaAluno() == null || dto.getVisivelParaAluno();
        registrarMovimentacao(processo, usuarioAutenticado, statusProcessoAnterior, dto.getStatus(), dto.getComentario(), visivel);

        return toResponse(processo, buscarMovimentacoes(processo.getId(), false));
    }

    // --- O ALUNO CANCELA UM REQUERIMENTO QUE AINDA NÃO FOI CONCLUÍDO ---
    @Transactional
    public ProcessoResponseDTO cancelar(Long id, String motivo) {
        Processo processo = buscarEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();

        if (!ehDonoDoProcesso(processo, usuarioAutenticado)) {
            throw new BusinessException("Apenas o aluno autor do processo pode cancelá-lo.");
        }

        StatusProcesso statusProcessoAnterior = processo.getStatus();
        validarTransicao(statusProcessoAnterior, StatusProcesso.CANCELADO);

        processo.setStatus(StatusProcesso.CANCELADO);
        processo.setEncerradoEm(LocalDateTime.now());
        processoRepository.save(processo);

        registrarMovimentacao(processo, usuarioAutenticado, statusProcessoAnterior, StatusProcesso.CANCELADO,
                motivo == null || motivo.isBlank() ? "Cancelado pelo aluno." : motivo, true);

        return toResponse(processo, buscarMovimentacoes(processo.getId(), true));
    }

    // --- BUSCA O PROCESSO PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    private Processo buscarEntidade(Long id) {
        return processoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Processo", id));
    }

    // --- SORTEIA UM PROTOCOLO ATÉ ENCONTRAR UM QUE AINDA NÃO ESTEJA EM USO ---
    private String gerarProtocoloUnico() {
        for (int tentativa = 0; tentativa < MAX_TENTATIVAS_PROTOCOLO; tentativa++) {
            String protocolo = geradorProtocolo.gerar(PREFIXO_PROTOCOLO);
            if (!processoRepository.existsByProtocolo(protocolo)) {
                return protocolo;
            }
        }
        throw new BusinessException("Não foi possível gerar um número de protocolo. Tente novamente.");
    }

    // --- GRAVA UM PASSO NA LINHA DO TEMPO DO PROCESSO ---
    private void registrarMovimentacao(Processo processo, Usuario autor, StatusProcesso anterior,
                   StatusProcesso novo, String comentario, boolean visivelParaAluno) {
        movimentacaoProcessoRepository.save(MovimentacaoProcesso.builder()
                .processo(processo)
                .autor(autor)
                .statusAnterior(anterior)
                .statusNovo(novo)
                .comentario(comentario)
                .visivelParaAluno(visivelParaAluno)
                .build());
    }

    // --- CARREGA A LINHA DO TEMPO, OCULTANDO OS DESPACHOS INTERNOS QUANDO O LEITOR É O ALUNO ---
    private List<MovimentacaoProcessoResponseDTO> buscarMovimentacoes(Long processoId, boolean somenteVisiveis) {
        List<MovimentacaoProcesso> movimentacoes = somenteVisiveis
                ? movimentacaoProcessoRepository.findByProcessoIdAndVisivelParaAlunoTrueOrderByCriadoEmAsc(processoId)
                : movimentacaoProcessoRepository.findByProcessoIdOrderByCriadoEmAsc(processoId);

        return movimentacoes.stream().map(this::toMovimentacaoResponse).toList();
    }

    // --- IMPEDE SALTOS DE STATUS INVÁLIDOS E MOVIMENTAÇÃO DE PROCESSO JÁ ENCERRADO ---
    private void validarTransicao(StatusProcesso atual, StatusProcesso novo) {
        if (atual.isFinal()) {
            throw new BusinessException("O processo já está %s e não pode ser movimentado.".formatted(atual));
        }

        if (atual == novo) {
            throw new BusinessException("O processo já está com o status %s.".formatted(novo));
        }

        if (!TRANSICOES.getOrDefault(atual, Set.of()).contains(novo)) {
            throw new BusinessException("Não é permitido mudar o status de %s para %s.".formatted(atual, novo));
        }
    }

    // --- RETORNA TRUE QUANDO O LEITOR SÓ PODE VER AS MOVIMENTAÇÕES PÚBLICAS ---
    private boolean validarPodeVisualizar(Processo processo, Usuario autenticado) {
        if (ehEquipeAdministrativa(autenticado)) {
            return false;
        }

        if (!ehDonoDoProcesso(processo, autenticado)) {
            throw new BusinessException("Você não tem permissão para consultar este processo.");
        }
        return true;
    }

    // --- VERIFICA SE O USUÁRIO AUTENTICADO É O ALUNO AUTOR DO PROCESSO ---
    private boolean ehDonoDoProcesso(Processo processo, Usuario autenticado) {
        Usuario dono = processo.getAluno().getUsuario();
        return dono != null && dono.getId().equals(autenticado.getId());
    }

    // --- PERFIS QUE TRAMITAM PROCESSOS ---
    private boolean ehEquipeAdministrativa(Usuario usuario) {
        return usuario.getRole() == TipoUsuario.ADMIN
                || usuario.getRole() == TipoUsuario.COORDENADOR
                || usuario.getRole() == TipoUsuario.SECRETARIA;
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA IDENTIFICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- RECUPERA O CADASTRO DE ALUNO VINCULADO AO USUÁRIO AUTENTICADO ---
    private Aluno alunoDoUsuarioOuFalha(Usuario usuario) {
        return alunoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui cadastro de aluno."));
    }

    // --- CONVERTE A ENTIDADE PROCESSO EM UM DTO DE RESPOSTA ---
    private ProcessoResponseDTO toResponse(Processo p, List<MovimentacaoProcessoResponseDTO> movimentacoes) {
        Usuario alunoUsuario = p.getAluno().getUsuario();

        return ProcessoResponseDTO.builder()
                .id(p.getId())
                .protocolo(p.getProtocolo())
                .alunoId(p.getAluno().getId())
                .alunoNome(alunoUsuario == null ? null : alunoUsuario.getNome())
                .alunoMatriculaRA(p.getAluno().getMatriculaRA())
                .tipo(p.getTipo())
                .assunto(p.getAssunto())
                .descricao(p.getDescricao())
                .status(p.getStatus())
                .responsavelId(p.getResponsavel() == null ? null : p.getResponsavel().getId())
                .responsavelNome(p.getResponsavel() == null ? null : p.getResponsavel().getNome())
                .prazoResposta(p.getPrazoResposta())
                .parecerFinal(p.getParecerFinal())
                .encerradoEm(p.getEncerradoEm())
                .criadoEm(p.getCriadoEm())
                .atualizadoEm(p.getAtualizadoEm())
                .movimentacoes(movimentacoes)
                .build();
    }

    // --- CONVERTE UMA MOVIMENTAÇÃO EM UM DTO DE RESPOSTA ---
    private MovimentacaoProcessoResponseDTO toMovimentacaoResponse(MovimentacaoProcesso m) {
        return MovimentacaoProcessoResponseDTO.builder()
                .id(m.getId())
                .autorNome(m.getAutor() == null ? null : m.getAutor().getNome())
                .statusAnterior(m.getStatusAnterior())
                .statusNovo(m.getStatusNovo())
                .comentario(m.getComentario())
                .visivelParaAluno(m.getVisivelParaAluno())
                .criadoEm(m.getCriadoEm())
                .build();
    }
}
