package erp.uniaura.modules.matricula.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.matricula.dto.AlterarStatusMatriculaRequestDTO;
import erp.uniaura.modules.matricula.dto.MatriculaRequestDTO;
import erp.uniaura.modules.matricula.dto.MatriculaResponseDTO;
import erp.uniaura.modules.matricula.model.Matricula;
import erp.uniaura.modules.matricula.model.StatusMatricula;
import erp.uniaura.modules.matricula.repository.MatriculaRepository;
import erp.uniaura.modules.turma.model.Turma;
import erp.uniaura.modules.turma.repository.TurmaRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- LISTA MATRÍCULAS ---
    @Transactional(readOnly = true)
    public Page<MatriculaResponseDTO> listar(Long alunoId, Pageable pageable) {
        Page<Matricula> page = (alunoId == null)
                ? matriculaRepository.findAll(pageable)
                : matriculaRepository.findByAlunoId(alunoId, pageable);
        return page.map(this::toResponse);
    }

    // --- BUSCA MATRÍCULA POR ID ---
    @Transactional(readOnly = true)
    public MatriculaResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    // --- EFETIVA A MATRÍCULA DE UM ALUNO EM UMA TURMA ---
    @Transactional
    public MatriculaResponseDTO matricular(MatriculaRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));

        Turma turma = turmaRepository.findById(dto.getTurmaId())
                .orElseThrow(() -> new ResourceNotFoundException("Turma", dto.getTurmaId()));

        if (!Boolean.TRUE.equals(turma.getAtiva())) {
            throw new BusinessException("Não é possível matricular alunos em uma turma inativa.");
        }

        if (matriculaRepository.existsByAlunoIdAndTurmaPeriodoLetivoAndStatus(
                aluno.getId(), turma.getPeriodoLetivo(), StatusMatricula.ATIVA)) {
            throw new BusinessException("O aluno já possui matrícula ATIVA no período letivo " + turma.getPeriodoLetivo() + ".");
        }

        long ocupadas = matriculaRepository.countByTurmaIdAndStatus(turma.getId(), StatusMatricula.ATIVA);
        if (ocupadas >= turma.getCapacidadeMaxima()) {
            throw new BusinessException("A turma atingiu a capacidade máxima de "
                    + turma.getCapacidadeMaxima() + " alunos.");
        }

        // --- CRIA A MATRÍCULA ---
        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .turma(turma)
                .dataMatricula(dto.getDataMatricula() != null ? dto.getDataMatricula() : LocalDate.now())
                .status(StatusMatricula.ATIVA)
                .observacoes(dto.getObservacoes())
                .criadaPor(usuarioAutenticado())
                .build();

        matricula = matriculaRepository.save(matricula);

        aluno.setTurmaAtualId(turma.getId());
        alunoRepository.save(aluno);

        return toResponse(matricula);
    }

    // --- TRANCA UMA MATRÍCULA ATIVA ---
    @Transactional
    public MatriculaResponseDTO trancar(Long id, AlterarStatusMatriculaRequestDTO alterarStatusMatriculaDTO) {
        return alterarStatus(id, StatusMatricula.TRANCADA, alterarStatusMatriculaDTO, true);
    }

    // --- CANCELA UMA MATRÍCULA ---
    @Transactional
    public MatriculaResponseDTO cancelar(Long id, AlterarStatusMatriculaRequestDTO alterarStatusMatriculaDTO) {
        return alterarStatus(id, StatusMatricula.CANCELADA, alterarStatusMatriculaDTO, true);
    }

    // --- ALTERA O STATUS DE UMA MATRÍCULA ATIVA E LIBERA A TURMA ATUAL DO ALUNO QUANDO NECESSÁRIO ---
    private MatriculaResponseDTO alterarStatus(Long id, StatusMatricula novoStatus,
           AlterarStatusMatriculaRequestDTO alterarStatusMatriculaDTO, boolean liberarTurmaAtualDoAluno) {
        Matricula matricula = buscarEntidade(id);

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new BusinessException("Só é possível alterar o status de uma matrícula que esteja ATIVA. "
                    + "Status atual: " + matricula.getStatus() + ".");
        }

        matricula.setStatus(novoStatus);
        if (alterarStatusMatriculaDTO != null && alterarStatusMatriculaDTO.getObservacoes() != null) {
            matricula.setObservacoes(alterarStatusMatriculaDTO.getObservacoes());
        }

        if (liberarTurmaAtualDoAluno) {
            Aluno aluno = matricula.getAluno();
            if (aluno.getTurmaAtualId() != null && aluno.getTurmaAtualId().equals(matricula.getTurma().getId())) {
                aluno.setTurmaAtualId(null);
                alunoRepository.save(aluno);
            }
        }

        return toResponse(matriculaRepository.save(matricula));
    }

    // --- BUSCA UMA MATRÍCULA PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELA NÃO SEJA ENCONTRADA ---
    private Matricula buscarEntidade(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula", id));
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO A PARTIR DO SECURITY CONTEXT ---
    private Usuario usuarioAutenticado() {
        return usuarioAutenticadoProvider.obter().orElse(null);
    }

    // --- CONVERTE A ENTIDADE MATRÍCULA EM UM DTO DE RESPOSTA ---
    private MatriculaResponseDTO toResponse(Matricula matricula) {
        Usuario usuarioCriador = matricula.getCriadaPor();
        return MatriculaResponseDTO.builder()
                .id(matricula.getId())
                .alunoId(matricula.getAluno().getId())
                .alunoNome(matricula.getAluno().getUsuario().getNome())
                .alunoMatriculaRA(matricula.getAluno().getMatriculaRA())
                .turmaId(matricula.getTurma().getId())
                .turmaCodigo(matricula.getTurma().getCodigo())
                .turmaPeriodoLetivo(matricula.getTurma().getPeriodoLetivo())
                .dataMatricula(matricula.getDataMatricula())
                .status(matricula.getStatus())
                .observacoes(matricula.getObservacoes())
                .criadaPorId(usuarioCriador != null ? usuarioCriador.getId() : null)
                .criadaPorNome(usuarioCriador != null ? usuarioCriador.getNome() : null)
                .criadoEm(matricula.getCriadoEm())
                .atualizadoEm(matricula.getAtualizadoEm())
                .build();
    }
}

