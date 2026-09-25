package erp.uniaura.modules.ouvidoria.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.protocolo.GeradorProtocolo;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.modules.ouvidoria.dto.ManifestacaoRequestDTO;
import erp.uniaura.modules.ouvidoria.dto.ManifestacaoResponseDTO;
import erp.uniaura.modules.ouvidoria.dto.ResponderManifestacaoRequestDTO;
import erp.uniaura.modules.ouvidoria.dto.RespostaManifestacaoResponseDTO;
import erp.uniaura.modules.ouvidoria.model.Manifestacao;
import erp.uniaura.modules.ouvidoria.model.RespostaManifestacao;
import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;
import erp.uniaura.modules.ouvidoria.model.TipoManifestacao;
import erp.uniaura.modules.ouvidoria.repository.ManifestacaoRepository;
import erp.uniaura.modules.ouvidoria.repository.RespostaManifestacaoRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OuvidoriaService {

    private static final String PREFIXO_PROTOCOLO = "OUV";

    private static final int MAX_TENTATIVAS_PROTOCOLO = 5;

    // --- TRANSIÇÕES PERMITIDAS PARA CADA STATUS ---
    private static final Map<StatusManifestacao, Set<StatusManifestacao>> TRANSICOES = new EnumMap<>(Map.of(
            StatusManifestacao.ABERTA,
            Set.of(StatusManifestacao.EM_APURACAO, StatusManifestacao.RESPONDIDA, StatusManifestacao.ARQUIVADA),
            StatusManifestacao.EM_APURACAO,
            Set.of(StatusManifestacao.RESPONDIDA, StatusManifestacao.ARQUIVADA),
            StatusManifestacao.RESPONDIDA,
            Set.of(StatusManifestacao.EM_APURACAO, StatusManifestacao.ENCERRADA, StatusManifestacao.ARQUIVADA)
    ));

    private final ManifestacaoRepository manifestacaoRepository;
    private final RespostaManifestacaoRepository respostaRepository;
    private final GeradorProtocolo geradorProtocolo;

    // --- O USUÁRIO AUTENTICADO REGISTRA UMA MANIFESTAÇÃO ---
    @Transactional
    public ManifestacaoResponseDTO registrar(ManifestacaoRequestDTO dto) {
        Usuario autenticado = usuarioAutenticadoOuFalha();

        Manifestacao manifestacao = Manifestacao.builder()
                .protocolo(gerarProtocoloUnico())
                .autor(autenticado)
                .anonima(dto.getAnonima() != null && dto.getAnonima())
                .tipo(dto.getTipo())
                .assunto(dto.getAssunto())
                .descricao(dto.getDescricao())
                .setor(dto.getSetor())
                .status(StatusManifestacao.ABERTA)
                .build();

        manifestacaoRepository.save(manifestacao);

        return toResponse(manifestacao, List.of());
    }

    // --- LISTA AS MANIFESTAÇÕES DO USUÁRIO AUTENTICADO ---
    @Transactional(readOnly = true)
    public Page<ManifestacaoResponseDTO> listarMinhas(StatusManifestacao status, Pageable pageable) {
        Usuario autenticado = usuarioAutenticadoOuFalha();

        Page<Manifestacao> pagina = status == null
                ? manifestacaoRepository.findByAutorId(autenticado.getId(), pageable)
                : manifestacaoRepository.findByAutorIdAndStatus(autenticado.getId(), status, pageable);

        return pagina.map(m -> toResponse(m, null));
    }

    // --- LISTAGEM DA OUVIDORIA COM FILTROS OPCIONAIS ---
    @Transactional(readOnly = true)
    public Page<ManifestacaoResponseDTO> listar(StatusManifestacao status, TipoManifestacao tipo, Pageable pageable) {
        return manifestacaoRepository.buscarComFiltros(status, tipo, pageable)
                .map(m -> toResponse(m, null));
    }

    // --- BUSCA UMA MANIFESTAÇÃO E O SEU HISTÓRICO DE RESPOSTAS ---
    @Transactional(readOnly = true)
    public ManifestacaoResponseDTO buscarPorId(UUID id) {
        Manifestacao manifestacao = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();
        boolean somenteVisiveis = validarPodeVisualizar(manifestacao, autenticado);

        return toResponse(manifestacao, buscarRespostas(manifestacao.getId(), somenteVisiveis));
    }

    // --- BUSCA UMA MANIFESTAÇÃO PELO NÚMERO DE PROTOCOLO ---
    @Transactional(readOnly = true)
    public ManifestacaoResponseDTO buscarPorProtocolo(String protocolo) {
        Manifestacao manifestacao = manifestacaoRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new ResourceNotFoundException("Manifestação", protocolo));
        Usuario autenticado = usuarioAutenticadoOuFalha();
        boolean somenteVisiveis = validarPodeVisualizar(manifestacao, autenticado);

        return toResponse(manifestacao, buscarRespostas(manifestacao.getId(), somenteVisiveis));
    }

    // --- A OUVIDORIA REGISTRA UMA RESPOSTA E MOVE A MANIFESTAÇÃO DE STATUS ---
    @Transactional
    public ManifestacaoResponseDTO responder(UUID id, ResponderManifestacaoRequestDTO dto) {
        Manifestacao manifestacao = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        StatusManifestacao anterior = manifestacao.getStatus();
        validarTransicao(anterior, dto.getStatus());

        manifestacao.setStatus(dto.getStatus());
        manifestacao.setResponsavel(autenticado);

        if (dto.getPrazoResposta() != null) {
            manifestacao.setPrazoResposta(dto.getPrazoResposta());
        }

        if (dto.getStatus() == StatusManifestacao.RESPONDIDA) {
            manifestacao.setRespostaFinal(dto.getMensagem());
            manifestacao.setRespondidoEm(LocalDateTime.now());
        }

        if (dto.getStatus().isFinal()) {
            manifestacao.setEncerradoEm(LocalDateTime.now());
        }

        manifestacaoRepository.save(manifestacao);

        boolean visivel = dto.getVisivelParaAutor() == null || dto.getVisivelParaAutor();
        respostaRepository.save(RespostaManifestacao.builder()
                .manifestacao(manifestacao)
                .autor(autenticado)
                .statusAnterior(anterior)
                .statusNovo(dto.getStatus())
                .mensagem(dto.getMensagem())
                .visivelParaAutor(visivel)
                .build());

        return toResponse(manifestacao, buscarRespostas(manifestacao.getId(), false));
    }

    // --- BUSCA A MANIFESTAÇÃO PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELA NÃO SEJA ENCONTRADA ---
    private Manifestacao buscarEntidade(UUID id) {
        return manifestacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manifestação", id));
    }

    // --- SORTEIA UM PROTOCOLO ATÉ ENCONTRAR UM QUE AINDA NÃO ESTEJA EM USO ---
    private String gerarProtocoloUnico() {
        for (int tentativa = 0; tentativa < MAX_TENTATIVAS_PROTOCOLO; tentativa++) {
            String protocolo = geradorProtocolo.gerar(PREFIXO_PROTOCOLO);
            if (!manifestacaoRepository.existsByProtocolo(protocolo)) {
                return protocolo;
            }
        }
        throw new BusinessException("Não foi possível gerar um número de protocolo. Tente novamente.");
    }

    // --- CARREGA O HISTÓRICO, OCULTANDO AS NOTAS INTERNAS QUANDO O LEITOR É O AUTOR ---
    private List<RespostaManifestacaoResponseDTO> buscarRespostas(UUID manifestacaoId, boolean somenteVisiveis) {
        List<RespostaManifestacao> respostas = somenteVisiveis
                ? respostaRepository.findByManifestacaoIdAndVisivelParaAutorTrueOrderByCriadoEmAsc(manifestacaoId)
                : respostaRepository.findByManifestacaoIdOrderByCriadoEmAsc(manifestacaoId);

        return respostas.stream().map(this::toRespostaResponse).toList();
    }

    // --- IMPEDE SALTOS DE STATUS INVÁLIDOS E RESPOSTA A MANIFESTAÇÃO JÁ ENCERRADA ---
    private void validarTransicao(StatusManifestacao atual, StatusManifestacao novo) {
        if (atual.isFinal()) {
            throw new BusinessException("A manifestação já está %s e não pode ser movimentada.".formatted(atual));
        }

        if (atual == novo) {
            throw new BusinessException("A manifestação já está com o status %s.".formatted(novo));
        }

        if (!TRANSICOES.getOrDefault(atual, Set.of()).contains(novo)) {
            throw new BusinessException("Não é permitido mudar o status de %s para %s.".formatted(atual, novo));
        }
    }

    // --- RETORNA TRUE QUANDO O LEITOR SÓ PODE VER AS RESPOSTAS PÚBLICAS ---
    private boolean validarPodeVisualizar(Manifestacao manifestacao, Usuario autenticado) {
        if (ehEquipeDaOuvidoria(autenticado)) {
            return false;
        }

        if (!manifestacao.getAutor().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não tem permissão para consultar esta manifestação.");
        }
        return true;
    }

    // --- PERFIS QUE APURAM MANIFESTAÇÕES ---
    private boolean ehEquipeDaOuvidoria(Usuario usuario) {
        return usuario.getRole() == TipoUsuario.ADMIN
                || usuario.getRole() == TipoUsuario.COORDENADOR
                || usuario.getRole() == TipoUsuario.SECRETARIA;
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA IDENTIFICADO ---
    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    // --- CONVERTE A ENTIDADE MANIFESTAÇÃO EM UM DTO DE RESPOSTA, PRESERVANDO O SIGILO DO AUTOR ---
    private ManifestacaoResponseDTO toResponse(Manifestacao m, List<RespostaManifestacaoResponseDTO> respostas) {
        boolean anonima = Boolean.TRUE.equals(m.getAnonima());

        return ManifestacaoResponseDTO.builder()
                .id(m.getId())
                .protocolo(m.getProtocolo())
                .tipo(m.getTipo())
                .assunto(m.getAssunto())
                .descricao(m.getDescricao())
                .setor(m.getSetor())
                .status(m.getStatus())
                .anonima(anonima)
                .autorId(anonima ? null : m.getAutor().getId())
                .autorNome(anonima ? null : m.getAutor().getNome())
                .responsavelId(m.getResponsavel() == null ? null : m.getResponsavel().getId())
                .responsavelNome(m.getResponsavel() == null ? null : m.getResponsavel().getNome())
                .prazoResposta(m.getPrazoResposta())
                .respostaFinal(m.getRespostaFinal())
                .respondidoEm(m.getRespondidoEm())
                .encerradoEm(m.getEncerradoEm())
                .criadoEm(m.getCriadoEm())
                .atualizadoEm(m.getAtualizadoEm())
                .respostas(respostas)
                .build();
    }

    // --- CONVERTE UMA RESPOSTA EM UM DTO DE RESPOSTA ---
    private RespostaManifestacaoResponseDTO toRespostaResponse(RespostaManifestacao r) {
        return RespostaManifestacaoResponseDTO.builder()
                .id(r.getId())
                .autorNome(r.getAutor() == null ? null : r.getAutor().getNome())
                .statusAnterior(r.getStatusAnterior())
                .statusNovo(r.getStatusNovo())
                .mensagem(r.getMensagem())
                .visivelParaAutor(r.getVisivelParaAutor())
                .criadoEm(r.getCriadoEm())
                .build();
    }
}
