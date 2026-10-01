package erp.uniaura.modules.atividade.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.infra.storage.StorageService;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.atividade.dto.AtividadeRequestDTO;
import erp.uniaura.modules.atividade.dto.AtividadeResponseDTO;
import erp.uniaura.modules.atividade.dto.AvaliarEntregaRequestDTO;
import erp.uniaura.modules.atividade.dto.EntregaAtividadeResponseDTO;
import erp.uniaura.modules.atividade.model.Atividade;
import erp.uniaura.modules.atividade.model.EntregaAtividade;
import erp.uniaura.modules.atividade.model.StatusEntrega;
import erp.uniaura.modules.atividade.repository.AtividadeRepository;
import erp.uniaura.modules.atividade.repository.EntregaAtividadeRepository;
import erp.uniaura.modules.matricula.model.StatusMatricula;
import erp.uniaura.modules.matricula.repository.MatriculaRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private static final String SUBDIR_ENTREGAS = "atividades/entregas";

    private final AtividadeRepository atividadeRepository;
    private final EntregaAtividadeRepository entregaAtividadeRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;
    private final StorageService storageService;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- LISTA AS ATIVIDADES DE UMA TURMA/DISCIPLINA ---
    @Transactional(readOnly = true)
    public Page<AtividadeResponseDTO> listarPorTurmaDisciplina(Long turmaDisciplinaId, Pageable pageable) {
        buscarTurmaDisciplina(turmaDisciplinaId);
        return atividadeRepository.findByTurmaDisciplinaId(turmaDisciplinaId, pageable)
                .map(this::toResponse);
    }

    // --- BUSCA UMA ATIVIDADE PELO ID ---
    @Transactional(readOnly = true)
    public AtividadeResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    // --- CRIAÇÃO DE UMA NOVA ATIVIDADE ---
    @Transactional
    public AtividadeResponseDTO criar(AtividadeRequestDTO dto) {
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplina(dto.getTurmaDisciplinaId());
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        Professor professor = validarProfessorDaDisciplina(turmaDisciplina, usuarioAutenticado);

        Atividade atividade = Atividade.builder()
                .turmaDisciplina(turmaDisciplina)
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .tipo(dto.getTipo())
                .dataPostagem(LocalDateTime.now())
                .dataEntrega(dto.getDataEntrega())
                .valorMaximo(dto.getValorMaximo())
                .professor(professor)
                .ativa(dto.getAtiva() == null ? Boolean.TRUE : dto.getAtiva())
                .build();

        if (atividade.getDataEntrega().isBefore(atividade.getDataPostagem())) {
            throw new BusinessException("A data de entrega não pode ser anterior à data de postagem.");
        }

        return toResponse(atividadeRepository.save(atividade));
    }

    // --- ATUALIZAÇÃO DE METADADOS DA ATIVIDADE ---
    @Transactional
    public AtividadeResponseDTO atualizar(Long id, AtividadeRequestDTO dto) {
        Atividade atividade = buscarEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        validarPodeEditarAtividade(atividade, usuarioAutenticado);

        if (!atividade.getTurmaDisciplina().getId().equals(dto.getTurmaDisciplinaId())) {
            atividade.setTurmaDisciplina(buscarTurmaDisciplina(dto.getTurmaDisciplinaId()));
        }

        atividade.setTitulo(dto.getTitulo());
        atividade.setDescricao(dto.getDescricao());
        atividade.setTipo(dto.getTipo());
        atividade.setDataEntrega(dto.getDataEntrega());
        atividade.setValorMaximo(dto.getValorMaximo());

        if (dto.getAtiva() != null) {
            atividade.setAtiva(dto.getAtiva());
        }

        if (atividade.getDataEntrega().isBefore(atividade.getDataPostagem())) {
            throw new BusinessException("A data de entrega não pode ser anterior à data de postagem.");
        }

        return toResponse(atividadeRepository.save(atividade));
    }

    // --- REMOVE A ATIVIDADE E OS ARQUIVOS DAS ENTREGAS VINCULADAS ---
    @Transactional
    public void deletar(Long id) {
        Atividade atividade = buscarEntidade(id);
        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        validarPodeEditarAtividade(atividade, usuarioAutenticado);

        entregaAtividadeRepository.findByAtividadeId(id).forEach(e -> storageService.delete(e.getArquivoUrl()));
        atividadeRepository.delete(atividade);
    }

    // --- ALUNO ENTREGA A ATIVIDADE ---
    @Transactional
    public EntregaAtividadeResponseDTO entregar(Long atividadeId, MultipartFile arquivo, String comentarioAluno) {
        Atividade atividade = buscarEntidade(atividadeId);

        if (Boolean.FALSE.equals(atividade.getAtiva())) {
            throw new BusinessException("Esta atividade não está mais ativa.");
        }

        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        Aluno aluno = alunoRepository.findByUsuarioId(usuarioAutenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário usuarioAutenticado não está vinculado a um aluno."));

        Long turmaId = atividade.getTurmaDisciplina().getTurma().getId();
        if (!matriculaRepository.existsByAlunoIdAndTurmaIdAndStatus(aluno.getId(), turmaId, StatusMatricula.ATIVA)) {
            throw new BusinessException("Aluno não possui matrícula ATIVA na turma desta atividade.");
        }

        LocalDateTime agora = LocalDateTime.now();
        StatusEntrega status = agora.isAfter(atividade.getDataEntrega()) ? StatusEntrega.ATRASADA : StatusEntrega.ENTREGUE;

        EntregaAtividade entrega = entregaAtividadeRepository.findByAtividadeIdAndAlunoId(atividadeId, aluno.getId())
                .orElseGet(() -> EntregaAtividade.builder()
                        .atividade(atividade)
                        .aluno(aluno)
                        .status(StatusEntrega.PENDENTE)
                        .build());

        if (entrega.getStatus() == StatusEntrega.AVALIADA) {
            throw new BusinessException("Esta entrega já foi avaliada e não pode mais ser modificada.");
        }

        if (entrega.getArquivoUrl() != null) {
            storageService.delete(entrega.getArquivoUrl());
        }

        String url = storageService.store(arquivo, SUBDIR_ENTREGAS);
        entrega.setArquivoUrl(url);
        entrega.setComentarioAluno(comentarioAluno);
        entrega.setDataEntrega(agora);
        entrega.setStatus(status);

        return toEntregaResponse(entregaAtividadeRepository.save(entrega));
    }

    // --- PROFESSOR AVALIA UMA ENTREGA ---
    @Transactional
    public EntregaAtividadeResponseDTO avaliar(Long entregaId, AvaliarEntregaRequestDTO dto) {
        EntregaAtividade entrega = entregaAtividadeRepository.findById(entregaId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega de atividade", entregaId));

        Usuario usuarioAutenticado = usuarioAutenticadoOuFalha();
        validarPodeEditarAtividade(entrega.getAtividade(), usuarioAutenticado);

        if (dto.getNota().doubleValue() > entrega.getAtividade().getValorMaximo().doubleValue()) {
            throw new BusinessException("Nota não pode ultrapassar o valor máximo da atividade ("
                    + entrega.getAtividade().getValorMaximo() + ").");
        }

        entrega.setNota(dto.getNota());
        entrega.setFeedback(dto.getFeedback());
        entrega.setStatus(StatusEntrega.AVALIADA);

        return toEntregaResponse(entregaAtividadeRepository.save(entrega));
    }

    // --- LISTA AS ENTREGAS DE UM ALUNO ---
    @Transactional(readOnly = true)
    public Page<EntregaAtividadeResponseDTO> listarEntregasDoAluno(Long alunoId, StatusEntrega status, Pageable pageable) {
        Page<EntregaAtividade> page = (status == null)
                ? entregaAtividadeRepository.findByAlunoId(alunoId, pageable)
                : entregaAtividadeRepository.findByAlunoIdAndStatus(alunoId, status, pageable);
        return page.map(this::toEntregaResponse);
    }

    // --- LISTA AS ENTREGAS DE UMA ATIVIDADE ---
    @Transactional(readOnly = true)
    public List<EntregaAtividadeResponseDTO> listarEntregasDaAtividade(Long atividadeId) {
        buscarEntidade(atividadeId);
        return entregaAtividadeRepository.findByAtividadeId(atividadeId)
                .stream()
                .map(this::toEntregaResponse)
                .toList();
    }

    // --- BUSCA A ATIVIDADE PELO ID OU LANÇA EXCEÇÃO SE NÃO EXISTIR ---
    private Atividade buscarEntidade(Long id) {
        return atividadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Atividade", id));
    }

    // --- BUSCA O VÍNCULO TURMA/DISCIPLINA PELO ID OU LANÇA EXCEÇÃO SE NÃO EXISTIR ---
    private TurmaDisciplina buscarTurmaDisciplina(Long id) {
        return turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", id));
    }

    // --- RECUPERA O Usuario AUTENTICADO A PARTIR DO SECURITYCONTEXT ---
    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }

    // --- VALIDA QUE O PROFESSOR LOGADO É O RESPONSÁVEL PELA DISCIPLINA ALOCADA NA TURMA ---
    private Professor validarProfessorDaDisciplina(TurmaDisciplina td, Usuario autenticado) {
        Professor professor = td.getProfessor();
        if (professor == null || professor.getUsuario() == null) {
            throw new BusinessException("Vínculo turma/disciplina não possui professor responsável.");
        }

        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return professor;
        }

        if (!professor.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pela disciplina pode realizar esta operação.");
        }
        return professor;
    }

    // --- VERIFICA SE O USUÁRIO LOGADO PODE EDITAR/AVALIAR A ATIVIDADE ---
    private void validarPodeEditarAtividade(Atividade atividade, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return;
        }

        if (!atividade.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pode editar/avaliar esta atividade.");
        }
    }

    // --- CONVERTE Atividade ---
    private AtividadeResponseDTO toResponse(Atividade a) {
        return AtividadeResponseDTO.builder()
                .id(a.getId())
                .turmaDisciplinaId(a.getTurmaDisciplina().getId())
                .titulo(a.getTitulo())
                .descricao(a.getDescricao())
                .tipo(a.getTipo())
                .dataPostagem(a.getDataPostagem())
                .dataEntrega(a.getDataEntrega())
                .valorMaximo(a.getValorMaximo())
                .professorId(a.getProfessor().getId())
                .professorNome(a.getProfessor().getUsuario() == null ? null : a.getProfessor().getUsuario().getNome())
                .ativa(a.getAtiva())
                .criadoEm(a.getCriadoEm())
                .atualizadoEm(a.getAtualizadoEm())
                .build();
    }

    // --- CONVERTE EntregaAtividade ---
    private EntregaAtividadeResponseDTO toEntregaResponse(EntregaAtividade e) {
        return EntregaAtividadeResponseDTO.builder()
                .id(e.getId())
                .atividadeId(e.getAtividade().getId())
                .atividadeTitulo(e.getAtividade().getTitulo())
                .alunoId(e.getAluno().getId())
                .alunoNome(e.getAluno().getUsuario() == null ? null : e.getAluno().getUsuario().getNome())
                .arquivoUrl(e.getArquivoUrl())
                .comentarioAluno(e.getComentarioAluno())
                .dataEntrega(e.getDataEntrega())
                .nota(e.getNota())
                .feedback(e.getFeedback())
                .status(e.getStatus())
                .criadoEm(e.getCriadoEm())
                .atualizadoEm(e.getAtualizadoEm())
                .build();
    }
}

