package erp.uniaura.modules.nota.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.infra.email.EmailService;
import erp.uniaura.infra.security.UsuarioAutenticadoProvider;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.repository.AlunoRepository;
import erp.uniaura.modules.nota.dto.BoletimResponseDTO;
import erp.uniaura.modules.nota.dto.DisciplinaBoletim;
import erp.uniaura.modules.nota.dto.NotaRequestDTO;
import erp.uniaura.modules.nota.dto.NotaResponseDTO;
import erp.uniaura.modules.nota.model.Nota;
import erp.uniaura.modules.nota.repository.NotaRepository;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
import erp.uniaura.modules.usuario.model.TipoUsuario;
import erp.uniaura.modules.usuario.model.Usuario;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotaService {

    private static final BigDecimal NOTA_MINIMA = new BigDecimal("0.00");
    private static final BigDecimal NOTA_MAXIMA = new BigDecimal("10.00");
    private static final BigDecimal PESO_DEFAULT = BigDecimal.ONE;

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final UsuarioAutenticadoProvider usuarioAutenticadoProvider;
    private final EmailService emailService;

    // --- LANÇA UMA NOVA NOTA ---
    @Transactional
    public NotaResponseDTO lancar(NotaRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", dto.getAlunoId()));

        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(dto.getTurmaDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", dto.getTurmaDisciplinaId()));

        validarValor(dto.getValor());

        Usuario usuarioAutenticado = usuarioAutenticadoObrigatorio();
        validarPermissaoLancamento(usuarioAutenticado, turmaDisciplina);

        Nota nota = Nota.builder()
                .aluno(aluno)
                .turmaDisciplina(turmaDisciplina)
                .periodoAvaliacao(dto.getPeriodoAvaliacao())
                .tipoAvaliacao(dto.getTipoAvaliacao())
                .valor(dto.getValor())
                .peso(dto.getPeso() != null ? dto.getPeso() : PESO_DEFAULT)
                .observacoes(dto.getObservacoes())
                .lancadaPor(usuarioAutenticado)
                .build();

        Nota salva = notaRepository.save(nota);
        notificarLancamento(salva);
        return toResponse(salva);
    }

    // --- NOTIFICA O ALUNO QUE UMA NOVA NOTA FOI LANÇADA PARA ELE ---
    private void notificarLancamento(Nota nota) {
        Usuario usuarioDoAluno = nota.getAluno().getUsuario();
        if (usuarioDoAluno == null) {
            return;
        }
        emailService.notificarNotaLancada(usuarioDoAluno.getEmail(), usuarioDoAluno.getNome(),
                nota.getTurmaDisciplina().getDisciplina().getNome(), nota.getValor());
    }

    // --- ATUALIZA UMA NOTA EXISTENTE ---
    @Transactional
    public NotaResponseDTO atualizar(Long id, NotaRequestDTO notaDTO) {
        Nota nota = buscarEntidade(id);

        validarValor(notaDTO.getValor());

        Usuario usuarioAutenticado = usuarioAutenticadoObrigatorio();
        validarPermissaoLancamento(usuarioAutenticado, nota.getTurmaDisciplina());

        nota.setPeriodoAvaliacao(notaDTO.getPeriodoAvaliacao());
        nota.setTipoAvaliacao(notaDTO.getTipoAvaliacao());
        nota.setValor(notaDTO.getValor());

        if (notaDTO.getPeso() != null) {
            nota.setPeso(notaDTO.getPeso());
        }
        nota.setObservacoes(notaDTO.getObservacoes());
        nota.setLancadaPor(usuarioAutenticado);

        return toResponse(notaRepository.save(nota));
    }

    // --- REMOVE UMA NOTA ---
    @Transactional
    public void deletar(Long id) {
        Nota nota = buscarEntidade(id);

        Usuario autenticado = usuarioAutenticadoObrigatorio();
        validarPermissaoLancamento(autenticado, nota.getTurmaDisciplina());

        notaRepository.delete(nota);
    }

    @Transactional(readOnly = true)
    public List<NotaResponseDTO> listarPorTurmaDisciplina(Long turmaId, Long disciplinaId) {
        return notaRepository
                .findByTurmaDisciplina_TurmaIdAndTurmaDisciplina_DisciplinaId(turmaId, disciplinaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // --- LISTA TODAS AS NOTAS DE UM ALUNO ---
    @Transactional(readOnly = true)
    public List<NotaResponseDTO> listarPorAluno(Long alunoId) {
        return notaRepository.findByAlunoId(alunoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // --- BOLETIM CONSOLIDADO DO ALUNO PARA UM PERÍODO LETIVO ---
    @Transactional(readOnly = true)
    public BoletimResponseDTO boletim(Long alunoId, String periodoLetivo) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno", alunoId));

        List<Nota> notas = notaRepository
                .findByAlunoIdAndTurmaDisciplina_TurmaPeriodoLetivo(alunoId, periodoLetivo);

        // --- AGRUPA NOTAS POR DISCIPLINA ---
        Map<Long, List<Nota>> porDisciplina = new LinkedHashMap<>();
        for (Nota nota : notas) {
            porDisciplina
                    .computeIfAbsent(nota.getTurmaDisciplina().getDisciplina().getId(), k -> new ArrayList<>())
                    .add(nota);
        }

        // --- CONSTRÓI CADA LINHA DO BOLETIM CALCULANDO A MÉDIA PONDERADA ---
        List<DisciplinaBoletim> linhas = porDisciplina.values().stream()
                .map(grupo -> {
                    var primeira = grupo.get(0).getTurmaDisciplina().getDisciplina();
                    return DisciplinaBoletim.builder()
                            .disciplinaId(primeira.getId())
                            .disciplinaCodigo(primeira.getCodigo())
                            .disciplinaNome(primeira.getNome())
                            .mediaFinal(calcularMediaPonderada(grupo))
                            .notas(grupo.stream()
                                    .sorted(Comparator.comparing(Nota::getPeriodoAvaliacao))
                                    .map(this::toResponse)
                                    .toList())
                            .build();
                })
                .toList();

        return BoletimResponseDTO.builder()
                .alunoId(aluno.getId())
                .alunoNome(aluno.getUsuario().getNome())
                .alunoMatriculaRA(aluno.getMatriculaRA())
                .periodoLetivo(periodoLetivo)
                .disciplinas(linhas)
                .build();
    }

    // --- VALIDA QUE A NOTA ESTÁ DENTRO DO INTERVALO PERMITIDO ---
    private void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(NOTA_MINIMA) < 0 || valor.compareTo(NOTA_MAXIMA) > 0) {
            throw new BusinessException("A nota deve estar entre " + NOTA_MINIMA + " e " + NOTA_MAXIMA + ".");
        }
    }

    // --- VALIDA QUE O USUÁRIO AUTENTICADO PODE LANÇAR/EDITAR NOTAS PARA AQUELA TURMA/DISCIPLINA ---
    private void validarPermissaoLancamento(Usuario autenticado, TurmaDisciplina td) {
        TipoUsuario role = autenticado.getRole();

        if (role == TipoUsuario.ADMIN || role == TipoUsuario.COORDENADOR) {
            return;
        }

        if (role == TipoUsuario.PROFESSOR) {
            Long profUsuarioId = td.getProfessor().getUsuario().getId();
            if (profUsuarioId.equals(autenticado.getId())) {
                return;
            }
            throw new BusinessException("Apenas o professor responsável pela disciplina pode lançar notas para esta turma.");
        }

        throw new BusinessException("Você não tem permissão para lançar notas.");
    }

    // --- CALCULA A MÉDIA PONDERADA DE UM CONJUNTO DE NOTAS ---
    private BigDecimal calcularMediaPonderada(List<Nota> notas) {
        BigDecimal somaPonderada = BigDecimal.ZERO;
        BigDecimal somaPesos = BigDecimal.ZERO;
        for (Nota nota : notas) {
            somaPonderada = somaPonderada.add(nota.getValor().multiply(nota.getPeso()));
            somaPesos = somaPesos.add(nota.getPeso());
        }

        if (somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return somaPonderada.divide(somaPesos, 2, RoundingMode.HALF_UP);
    }

    // --- BUSCA A ENTIDADE ---
    private Nota buscarEntidade(Long id) {
        return notaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nota", id));
    }

    // --- RECUPERA O USUÁRIO AUTENTICADO ---
    private Usuario usuarioAutenticadoObrigatorio() {
        return usuarioAutenticadoProvider.obter()
                .orElseThrow(() -> new BusinessException("Não foi possível identificar o usuário autenticado."));
    }

    // --- CONVERSÃO ENTIDADE ---
    private NotaResponseDTO toResponse(Nota nota) {
        TurmaDisciplina turmaDisciplina = nota.getTurmaDisciplina();
        Usuario usuarioLancador = nota.getLancadaPor();
        return NotaResponseDTO.builder()
                .id(nota.getId())
                .alunoId(nota.getAluno().getId())
                .alunoNome(nota.getAluno().getUsuario().getNome())
                .alunoMatriculaRA(nota.getAluno().getMatriculaRA())
                .turmaDisciplinaId(turmaDisciplina.getId())
                .turmaId(turmaDisciplina.getTurma().getId())
                .turmaCodigo(turmaDisciplina.getTurma().getCodigo())
                .disciplinaId(turmaDisciplina.getDisciplina().getId())
                .disciplinaNome(turmaDisciplina.getDisciplina().getNome())
                .professorId(turmaDisciplina.getProfessor().getId())
                .professorNome(turmaDisciplina.getProfessor().getUsuario().getNome())
                .periodoAvaliacao(nota.getPeriodoAvaliacao())
                .tipoAvaliacao(nota.getTipoAvaliacao())
                .valor(nota.getValor())
                .peso(nota.getPeso())
                .observacoes(nota.getObservacoes())
                .lancadaPorId(usuarioLancador != null ? usuarioLancador.getId() : null)
                .lancadaPorNome(usuarioLancador != null ? usuarioLancador.getNome() : null)
                .lancadaEm(nota.getLancadaEm())
                .atualizadaEm(nota.getAtualizadaEm())
                .build();
    }
}

