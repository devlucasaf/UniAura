package erp.uniaura.modules.coordenacao.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.coordenacao.dto.CargaHorariaProfessorDTO;
import erp.uniaura.modules.coordenacao.repository.AlocacaoDocenteRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorDisciplinaRepository;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.turma.dto.TurmaDisciplinaResponseDTO;
import erp.uniaura.modules.turma.model.Turma;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;
import erp.uniaura.modules.turma.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlocacaoDocenteService {

    private static final int ESCALA = 2;

    private static final BigDecimal MINUTOS_POR_HORA = new BigDecimal("60");

    private final AlocacaoDocenteRepository alocacaoRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final ProfessorDisciplinaRepository professorDisciplinaRepository;

    // --- TROCA O PROFESSOR RESPONSÁVEL POR UMA TURMA/DISCIPLINA JÁ EXISTENTE ---
    @Transactional
    public TurmaDisciplinaResponseDTO alocarProfessor(UUID turmaDisciplinaId, UUID professorId) {
        TurmaDisciplina vinculo = buscarVinculo(turmaDisciplinaId);
        Professor professor = buscarProfessor(professorId);

        if (vinculo.getProfessor().getId().equals(professorId)) {
            throw new BusinessException("O professor informado já é o responsável por esta turma/disciplina.");
        }

        validarHabilitacao(professor, vinculo);
        validarConflitoDeHorario(professor, vinculo);
        validarCargaHoraria(professor, vinculo);

        vinculo.setProfessor(professor);
        return toResponse(turmaDisciplinaRepository.save(vinculo));
    }

    // --- DEFINE OU REMOVE O PROFESSOR REGENTE DE UMA TURMA ---
    @Transactional
    public void definirProfessorRegente(UUID turmaId, UUID professorId) {
        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new ResourceNotFoundException("Turma", turmaId));

        turma.setProfessorRegente(professorId == null ? null : buscarProfessor(professorId));
        turmaRepository.save(turma);
    }

    // --- CONSOLIDA A CARGA HORÁRIA SEMANAL JÁ COMPROMETIDA DE UM PROFESSOR ---
    @Transactional(readOnly = true)
    public CargaHorariaProfessorDTO consultarCargaHoraria(UUID professorId, String periodoLetivo) {
        Professor professor = buscarProfessor(professorId);
        String periodo = (periodoLetivo == null || periodoLetivo.isBlank()) ? null : periodoLetivo;

        List<TurmaDisciplina> alocacoes = alocacaoRepository.buscarAlocacoesDoProfessor(professorId, periodo);
        BigDecimal alocadas = somarHorasSemanais(alocacoes);

        Integer contratada = professor.getCargaHorariaSemanal();
        BigDecimal disponiveis = contratada == null
                ? null
                : BigDecimal.valueOf(contratada).subtract(alocadas).setScale(ESCALA, RoundingMode.HALF_UP);

        return CargaHorariaProfessorDTO.builder()
                .professorId(professor.getId())
                .professorNome(professor.getUsuario() == null ? null : professor.getUsuario().getNome())
                .periodoLetivo(periodo)
                .horasSemanaisAlocadas(alocadas)
                .cargaHorariaSemanalContratada(contratada)
                .horasSemanaisDisponiveis(disponiveis)
                .alocacoes(alocacoes.stream().map(this::toResponse).toList())
                .build();
    }

    // --- HELPERS ---

    private TurmaDisciplina buscarVinculo(UUID id) {
        return turmaDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", id));
    }

    private Professor buscarProfessor(UUID id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor", id));
    }

    // --- O PROFESSOR PRECISA ESTAR HABILITADO A LECIONAR A DISCIPLINA, MESMA REGRA APLICADA POR TurmaService ---
    private void validarHabilitacao(Professor professor, TurmaDisciplina vinculo) {
        UUID disciplinaId = vinculo.getDisciplina().getId();
        if (!professorDisciplinaRepository.existsByProfessorIdAndDisciplinaId(professor.getId(), disciplinaId)) {
            throw new BusinessException("O professor selecionado não está habilitado a lecionar a disciplina informada.");
        }
    }

    // --- IMPEDE QUE O PROFESSOR FIQUE COM DUAS AULAS SOBREPOSTAS NO MESMO PERÍODO LETIVO ---
    private void validarConflitoDeHorario(Professor professor, TurmaDisciplina vinculo) {
        List<TurmaDisciplina> conflitos = alocacaoRepository.buscarConflitosDeHorario(
                professor.getId(),
                vinculo.getDiaSemana(),
                vinculo.getTurma().getPeriodoLetivo(),
                vinculo.getHorarioInicio(),
                vinculo.getHorarioFim(),
                vinculo.getId());

        if (conflitos.isEmpty()) {
            return;
        }

        TurmaDisciplina conflito = conflitos.get(0);
        throw new BusinessException(
                "Conflito de horário: o professor já leciona %s na turma %s em %s das %s às %s."
                        .formatted(conflito.getDisciplina().getNome(),
                                conflito.getTurma().getCodigo(),
                                conflito.getDiaSemana(),
                                conflito.getHorarioInicio(),
                                conflito.getHorarioFim()));
    }

    // --- IMPEDE QUE A ALOCAÇÃO ULTRAPASSE A CARGA HORÁRIA SEMANAL CONTRATADA ---
    private void validarCargaHoraria(Professor professor, TurmaDisciplina vinculo) {
        Integer contratada = professor.getCargaHorariaSemanal();
        if (contratada == null) {
            return;
        }

        String periodoLetivo = vinculo.getTurma().getPeriodoLetivo();
        List<TurmaDisciplina> atuais = alocacaoRepository.buscarAlocacoesDoProfessor(professor.getId(), periodoLetivo);

        BigDecimal jaAlocadas = somarHorasSemanais(atuais);
        BigDecimal novaAula = horasDe(vinculo);
        BigDecimal total = jaAlocadas.add(novaAula);

        if (total.compareTo(BigDecimal.valueOf(contratada)) > 0) {
            throw new BusinessException(
                    "A alocação ultrapassa a carga horária semanal do professor: %s h já alocadas + %s h desta aula excedem as %d h contratadas."
                            .formatted(jaAlocadas, novaAula, contratada));
        }
    }

    // --- SOMA A DURAÇÃO SEMANAL DAS AULAS INFORMADAS ---
    private BigDecimal somarHorasSemanais(List<TurmaDisciplina> alocacoes) {
        BigDecimal total = BigDecimal.ZERO;
        for (TurmaDisciplina td : alocacoes) {
            total = total.add(horasDe(td));
        }
        return total.setScale(ESCALA, RoundingMode.HALF_UP);
    }

    // --- CONVERTE A FAIXA DE HORÁRIO DE UMA AULA EM HORAS ---
    private BigDecimal horasDe(TurmaDisciplina td) {
        long minutos = Duration.between(td.getHorarioInicio(), td.getHorarioFim()).toMinutes();
        return BigDecimal.valueOf(minutos).divide(MINUTOS_POR_HORA, ESCALA, RoundingMode.HALF_UP);
    }

    // --- CONVERTE O VÍNCULO ENTRE TURMA E DISCIPLINA EM UM DTO DE RESPOSTA ---
    private TurmaDisciplinaResponseDTO toResponse(TurmaDisciplina v) {
        return TurmaDisciplinaResponseDTO.builder()
                .id(v.getId())
                .turmaId(v.getTurma().getId())
                .disciplinaId(v.getDisciplina().getId())
                .disciplinaCodigo(v.getDisciplina().getCodigo())
                .disciplinaNome(v.getDisciplina().getNome())
                .professorId(v.getProfessor().getId())
                .professorNome(v.getProfessor().getUsuario() == null ? null : v.getProfessor().getUsuario().getNome())
                .diaSemana(v.getDiaSemana())
                .horarioInicio(v.getHorarioInicio())
                .horarioFim(v.getHorarioFim())
                .criadoEm(v.getCriadoEm())
                .build();
    }
}
