package erp.uniaura.modules.avaliacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.avaliacao.dto.AvaliacaoDocenteRequestDTO;
import erp.uniaura.modules.avaliacao.dto.ComentarioAvaliacaoResponseDTO;
import erp.uniaura.modules.avaliacao.dto.MediaAvaliacaoResponseDTO;
import erp.uniaura.modules.avaliacao.model.AvaliacaoDocente;
import erp.uniaura.modules.avaliacao.repository.AvaliacaoDocenteRepository;
import erp.uniaura.modules.matricula.model.StatusMatricula;
import erp.uniaura.modules.matricula.repository.MatriculaRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoDocenteService {

    private static final int ESCALA = 2;

    private final AvaliacaoDocenteRepository avaliacaoDocenteRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;

    // --- REGISTRA A AVALIAÇÃO DO ALUNO AUTENTICADO SOBRE O PROFESSOR DE UMA TURMA/DISCIPLINA ---
    @Transactional
    public void avaliar(AvaliacaoDocenteRequestDTO dto) {
        Aluno aluno = alunoDoAutenticado();
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(dto.getTurmaDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", dto.getTurmaDisciplinaId()));

        boolean matriculado = matriculaRepository.existsByAlunoIdAndTurmaIdAndStatus(
                aluno.getId(), turmaDisciplina.getTurma().getId(), StatusMatricula.ATIVA);
        if (!matriculado) {
            throw new BusinessException("Você só pode avaliar disciplinas de turmas em que está matriculado.");
        }

        if (avaliacaoDocenteRepository.existsByAlunoIdAndTurmaDisciplinaId(aluno.getId(), turmaDisciplina.getId())) {
            throw new BusinessException("Você já avaliou esta disciplina.");
        }

        AvaliacaoDocente avaliacao = AvaliacaoDocente.builder()
                .aluno(aluno)
                .turmaDisciplina(turmaDisciplina)
                .notaDidatica(dto.getNotaDidatica())
                .notaPontualidade(dto.getNotaPontualidade())
                .notaDisponibilidade(dto.getNotaDisponibilidade())
                .comentario(dto.getComentario())
                .build();

        avaliacaoDocenteRepository.save(avaliacao);
    }

    // --- MÉDIA DAS AVALIAÇÕES DE UMA TURMA/DISCIPLINA ESPECÍFICA ---
    @Transactional(readOnly = true)
    public MediaAvaliacaoResponseDTO mediaPorTurmaDisciplina(Long turmaDisciplinaId) {
        return toMedia(avaliacaoDocenteRepository.resumoPorTurmaDisciplina(turmaDisciplinaId));
    }

    // --- MÉDIA CONSOLIDADA DE TODAS AS TURMAS/DISCIPLINAS DE UM PROFESSOR ---
    @Transactional(readOnly = true)
    public MediaAvaliacaoResponseDTO mediaPorProfessor(Long professorId) {
        return toMedia(avaliacaoDocenteRepository.resumoPorProfessor(professorId));
    }

    // --- MÉDIA CONSOLIDADA DO PROFESSOR AUTENTICADO ---
    @Transactional(readOnly = true)
    public MediaAvaliacaoResponseDTO meuDesempenho() {
        Professor professor = professorDoAutenticado();
        return mediaPorProfessor(professor.getId());
    }

    // --- COMENTÁRIOS ANÔNIMOS DEIXADOS PELOS ALUNOS SOBRE UMA TURMA/DISCIPLINA ---
    @Transactional(readOnly = true)
    public List<ComentarioAvaliacaoResponseDTO> comentarios(Long turmaDisciplinaId) {
        return avaliacaoDocenteRepository.findByTurmaDisciplinaId(turmaDisciplinaId).stream()
                .filter(a -> a.getComentario() != null && !a.getComentario().isBlank())
                .map(a -> ComentarioAvaliacaoResponseDTO.builder()
                        .notaGeral(mediaDasTresNotas(a))
                        .comentario(a.getComentario())
                        .criadoEm(a.getCriadoEm())
                        .build())
                .toList();
    }

    // --- MÉDIA SIMPLES DAS TRÊS NOTAS DE UMA AVALIAÇÃO INDIVIDUAL ---
    private BigDecimal mediaDasTresNotas(AvaliacaoDocente a) {
        BigDecimal soma = BigDecimal.valueOf(a.getNotaDidatica() + a.getNotaPontualidade() + a.getNotaDisponibilidade());
        return soma.divide(BigDecimal.valueOf(3), ESCALA, RoundingMode.HALF_UP);
    }

    // --- CONVERTE A LINHA AGREGADA (COUNT, AVG, AVG, AVG) NO DTO DE MÉDIAS ---
    private MediaAvaliacaoResponseDTO toMedia(Object[] linha) {
        long quantidade = ((Number) linha[0]).longValue();
        BigDecimal mediaDidatica = arredondar(linha[1]);
        BigDecimal mediaPontualidade = arredondar(linha[2]);
        BigDecimal mediaDisponibilidade = arredondar(linha[3]);
        BigDecimal mediaGeral = mediaDidatica.add(mediaPontualidade).add(mediaDisponibilidade)
                .divide(BigDecimal.valueOf(3), ESCALA, RoundingMode.HALF_UP);

        return MediaAvaliacaoResponseDTO.builder()
                .quantidadeAvaliacoes(quantidade)
                .mediaDidatica(mediaDidatica)
                .mediaPontualidade(mediaPontualidade)
                .mediaDisponibilidade(mediaDisponibilidade)
                .mediaGeral(mediaGeral)
                .build();
    }

    private BigDecimal arredondar(Object valor) {
        return new BigDecimal(valor.toString()).setScale(ESCALA, RoundingMode.HALF_UP);
    }

    // --- RESOLVE O ALUNO ASSOCIADO AO USUÁRIO AUTENTICADO ---
    private Aluno alunoDoAutenticado() {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return alunoRepository.findByUsuarioId(autenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui matrícula de aluno."));
    }

    // --- RESOLVE O PROFESSOR ASSOCIADO AO USUÁRIO AUTENTICADO ---
    private Professor professorDoAutenticado() {
        Usuario autenticado = usuarioAutenticadoOuFalha();
        return professorRepository.findByUsuarioId(autenticado.getId())
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui vínculo de professor."));
    }

    private Usuario usuarioAutenticadoOuFalha() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Usuário autenticado não identificado."));
    }
}
