package erp.uniaura.modules.coordenacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioDetails;
import erp.uniaura.modules.coordenacao.dto.AvaliacaoPlanoEnsinoResponseDTO;
import erp.uniaura.modules.coordenacao.dto.AvaliarPlanoEnsinoRequestDTO;
import erp.uniaura.modules.coordenacao.dto.PlanoEnsinoRequestDTO;
import erp.uniaura.modules.coordenacao.dto.PlanoEnsinoResponseDTO;
import erp.uniaura.modules.coordenacao.model.AvaliacaoPlanoEnsino;
import erp.uniaura.modules.coordenacao.model.PlanoEnsino;
import erp.uniaura.modules.coordenacao.model.StatusPlanoEnsino;
import erp.uniaura.modules.coordenacao.repository.AvaliacaoPlanoEnsinoRepository;
import erp.uniaura.modules.coordenacao.repository.PlanoEnsinoRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanoEnsinoService {

    // --- DESFECHOS ACEITOS NA AVALIAÇÃO DA COORDENAÇÃO ---
    private static final Set<StatusPlanoEnsino> DESFECHOS = Set.of(
            StatusPlanoEnsino.APROVADO,
            StatusPlanoEnsino.DEVOLVIDO,
            StatusPlanoEnsino.REPROVADO);

    private final PlanoEnsinoRepository planoRepository;
    private final AvaliacaoPlanoEnsinoRepository avaliacaoRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final ProfessorRepository professorRepository;

    // --- O PROFESSOR CRIA O PLANO DA SUA TURMA/DISCIPLINA EM RASCUNHO ---
    @Transactional
    public PlanoEnsinoResponseDTO criar(PlanoEnsinoRequestDTO dto) {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        TurmaDisciplina td = buscarTurmaDisciplina(dto.getTurmaDisciplinaId());

        if (planoRepository.existsByTurmaDisciplinaId(td.getId())) {
            throw new BusinessException("Já existe um plano de ensino para esta turma/disciplina.");
        }

        validarPodeEditarConteudo(td, autenticado);

        PlanoEnsino plano = PlanoEnsino.builder()
                .turmaDisciplina(td)
                .professor(td.getProfessor())
                .status(StatusPlanoEnsino.RASCUNHO)
                .build();

        aplicarConteudo(plano, dto);
        planoRepository.save(plano);

        registrarHistorico(plano, autenticado, null, StatusPlanoEnsino.RASCUNHO, "Plano de ensino criado.");

        return toResponse(plano, null);
    }

    // --- ATUALIZA O CONTEÚDO ENQUANTO O PLANO AINDA É EDITÁVEL ---
    @Transactional
    public PlanoEnsinoResponseDTO atualizar(UUID id, PlanoEnsinoRequestDTO dto) {
        PlanoEnsino plano = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        validarPodeEditarConteudo(plano.getTurmaDisciplina(), autenticado);

        if (!plano.getStatus().isEditavel()) {
            throw new BusinessException(
                    "O plano está %s e só pode ser editado em RASCUNHO ou DEVOLVIDO.".formatted(plano.getStatus()));
        }

        aplicarConteudo(plano, dto);
        planoRepository.save(plano);

        return toResponse(plano, buscarHistorico(plano.getId()));
    }

    // --- O PROFESSOR ENVIA O PLANO PARA A COORDENAÇÃO ---
    @Transactional
    public PlanoEnsinoResponseDTO submeter(UUID id) {
        PlanoEnsino plano = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        validarPodeEditarConteudo(plano.getTurmaDisciplina(), autenticado);

        if (!plano.getStatus().isEditavel()) {
            throw new BusinessException(
                    "Somente um plano em RASCUNHO ou DEVOLVIDO pode ser submetido. Status atual: %s."
                            .formatted(plano.getStatus()));
        }

        StatusPlanoEnsino anterior = plano.getStatus();
        plano.setStatus(StatusPlanoEnsino.SUBMETIDO);
        plano.setSubmetidoEm(LocalDateTime.now());
        planoRepository.save(plano);

        registrarHistorico(plano, autenticado, anterior, StatusPlanoEnsino.SUBMETIDO,
                "Plano submetido para avaliação da coordenação.");

        return toResponse(plano, buscarHistorico(plano.getId()));
    }

    // --- A COORDENAÇÃO APROVA, DEVOLVE PARA AJUSTE OU REPROVA ---
    @Transactional
    public PlanoEnsinoResponseDTO avaliar(UUID id, AvaliarPlanoEnsinoRequestDTO dto) {
        PlanoEnsino plano = buscarEntidade(id);
        Usuario autenticado = usuarioAutenticadoOuFalha();

        if (!DESFECHOS.contains(dto.getStatus())) {
            throw new BusinessException("A avaliação deve resultar em APROVADO, DEVOLVIDO ou REPROVADO.");
        }

        if (plano.getStatus() != StatusPlanoEnsino.SUBMETIDO) {
            throw new BusinessException(
                    "Somente um plano SUBMETIDO pode ser avaliado. Status atual: %s.".formatted(plano.getStatus()));
        }

        boolean exigeParecer = dto.getStatus() != StatusPlanoEnsino.APROVADO;
        if (exigeParecer && (dto.getParecer() == null || dto.getParecer().isBlank())) {
            throw new BusinessException("É obrigatório registrar o parecer ao devolver ou reprovar um plano de ensino.");
        }

        StatusPlanoEnsino anterior = plano.getStatus();
        plano.setStatus(dto.getStatus());
        plano.setAvaliadoPor(autenticado);
        plano.setParecer(dto.getParecer());
        plano.setAvaliadoEm(LocalDateTime.now());
        planoRepository.save(plano);

        registrarHistorico(plano, autenticado, anterior, dto.getStatus(), dto.getParecer());

        return toResponse(plano, buscarHistorico(plano.getId()));
    }

    // --- FILA DA COORDENAÇÃO ---
    @Transactional(readOnly = true)
    public Page<PlanoEnsinoResponseDTO> listar(StatusPlanoEnsino status, UUID cursoId,
                                               String periodoLetivo, Pageable pageable) {
        String periodo = (periodoLetivo == null || periodoLetivo.isBlank()) ? null : periodoLetivo;
        return planoRepository.buscarComFiltros(status, cursoId, periodo, pageable)
                .map(p -> toResponse(p, null));
    }

    // --- PLANOS DO PROFESSOR AUTENTICADO ---
    @Transactional(readOnly = true)
    public Page<PlanoEnsinoResponseDTO> listarMeusPlanos(Pageable pageable) {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return planoRepository.findByProfessorId(professorDoUsuarioOuFalha(autenticado), pageable)
                .map(p -> toResponse(p, null));
    }

    @Transactional(readOnly = true)
    public PlanoEnsinoResponseDTO buscarPorId(UUID id) {
        PlanoEnsino plano = buscarEntidade(id);
        return toResponse(plano, buscarHistorico(plano.getId()));
    }

    @Transactional(readOnly = true)
    public PlanoEnsinoResponseDTO buscarPorTurmaDisciplina(UUID turmaDisciplinaId) {
        PlanoEnsino plano = planoRepository.findByTurmaDisciplinaId(turmaDisciplinaId)
                .orElseThrow(() -> new ResourceNotFoundException("Plano de ensino (turma/disciplina)", turmaDisciplinaId));
        return toResponse(plano, buscarHistorico(plano.getId()));
    }

    // --- HELPERS ---

    private PlanoEnsino buscarEntidade(UUID id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plano de ensino", id));
    }

    private TurmaDisciplina buscarTurmaDisciplina(UUID id) {
        return turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", id));
    }

    // --- COPIA OS CAMPOS DO DTO PARA A ENTIDADE ---
    private void aplicarConteudo(PlanoEnsino plano, PlanoEnsinoRequestDTO dto) {
        plano.setEmenta(dto.getEmenta());
        plano.setObjetivos(dto.getObjetivos());
        plano.setConteudoProgramatico(dto.getConteudoProgramatico());
        plano.setMetodologia(dto.getMetodologia());
        plano.setCriteriosAvaliacao(dto.getCriteriosAvaliacao());
        plano.setBibliografiaBasica(dto.getBibliografiaBasica());
        plano.setBibliografiaComplementar(dto.getBibliografiaComplementar());
    }

    // --- GRAVA UM PASSO NO HISTÓRICO DO PLANO ---
    private void registrarHistorico(PlanoEnsino plano, Usuario autor, StatusPlanoEnsino anterior,
                                    StatusPlanoEnsino novo, String parecer) {
        avaliacaoRepository.save(AvaliacaoPlanoEnsino.builder()
                .planoEnsino(plano)
                .autor(autor)
                .statusAnterior(anterior)
                .statusNovo(novo)
                .parecer(parecer)
                .build());
    }

    private List<AvaliacaoPlanoEnsinoResponseDTO> buscarHistorico(UUID planoId) {
        return avaliacaoRepository.findByPlanoEnsinoIdOrderByCriadoEmAsc(planoId)
                .stream()
                .map(a -> AvaliacaoPlanoEnsinoResponseDTO.builder()
                        .id(a.getId())
                        .autorNome(a.getAutor() == null ? null : a.getAutor().getNome())
                        .statusAnterior(a.getStatusAnterior())
                        .statusNovo(a.getStatusNovo())
                        .parecer(a.getParecer())
                        .criadoEm(a.getCriadoEm())
                        .build())
                .toList();
    }

    // --- APENAS O PROFESSOR RESPONSÁVEL PELA TURMA/DISCIPLINA, A COORDENAÇÃO OU O ADMIN EDITAM O CONTEÚDO ---
    private void validarPodeEditarConteudo(TurmaDisciplina td, Usuario autenticado) {
        TipoUsuario role = autenticado.getRole();
        if (role == TipoUsuario.ADMIN || role == TipoUsuario.COORDENADOR) {
            return;
        }

        Professor professor = td.getProfessor();
        if (professor.getUsuario() == null || !professor.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pela disciplina pode editar este plano de ensino.");
        }
    }

    // --- RECUPERA O IDENTIFICADOR DO PROFESSOR VINCULADO AO USUÁRIO AUTENTICADO ---
    private UUID professorDoUsuarioOuFalha(Usuario usuario) {
        return professorRepository.findByUsuarioId(usuario.getId())
                .map(Professor::getId)
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui cadastro de professor."));
    }

    private Usuario usuarioAutenticadoOuFalha() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UsuarioDetails ud)) {
            throw new BusinessException("Usuário autenticado não identificado.");
        }
        return ud.getUsuario();
    }

    // --- CONVERTE A ENTIDADE PLANO DE ENSINO EM UM DTO DE RESPOSTA ---
    private PlanoEnsinoResponseDTO toResponse(PlanoEnsino p, List<AvaliacaoPlanoEnsinoResponseDTO> historico) {
        TurmaDisciplina td = p.getTurmaDisciplina();

        return PlanoEnsinoResponseDTO.builder()
                .id(p.getId())
                .turmaDisciplinaId(td.getId())
                .turmaId(td.getTurma().getId())
                .turmaCodigo(td.getTurma().getCodigo())
                .periodoLetivo(td.getTurma().getPeriodoLetivo())
                .disciplinaId(td.getDisciplina().getId())
                .disciplinaCodigo(td.getDisciplina().getCodigo())
                .disciplinaNome(td.getDisciplina().getNome())
                .professorId(p.getProfessor().getId())
                .professorNome(p.getProfessor().getUsuario() == null ? null : p.getProfessor().getUsuario().getNome())
                .ementa(p.getEmenta())
                .objetivos(p.getObjetivos())
                .conteudoProgramatico(p.getConteudoProgramatico())
                .metodologia(p.getMetodologia())
                .criteriosAvaliacao(p.getCriteriosAvaliacao())
                .bibliografiaBasica(p.getBibliografiaBasica())
                .bibliografiaComplementar(p.getBibliografiaComplementar())
                .status(p.getStatus())
                .avaliadoPorId(p.getAvaliadoPor() == null ? null : p.getAvaliadoPor().getId())
                .avaliadoPorNome(p.getAvaliadoPor() == null ? null : p.getAvaliadoPor().getNome())
                .parecer(p.getParecer())
                .submetidoEm(p.getSubmetidoEm())
                .avaliadoEm(p.getAvaliadoEm())
                .criadoEm(p.getCriadoEm())
                .atualizadoEm(p.getAtualizadoEm())
                .historico(historico)
                .build();
    }
}
