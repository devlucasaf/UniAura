package erp.uniaura.modules.frequencia.service;

import erp.uniaura.exception.BusinessException;
import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.frequencia.dto.*;
import erp.uniaura.modules.frequencia.model.Aula;
import erp.uniaura.modules.frequencia.model.Frequencia;
import erp.uniaura.modules.frequencia.repository.AulaRepository;
import erp.uniaura.modules.frequencia.repository.FrequenciaRepository;
import erp.uniaura.modules.matricula.model.Matricula;
import erp.uniaura.modules.matricula.model.StatusMatricula;
import erp.uniaura.modules.matricula.repository.MatriculaRepository;
import erp.uniaura.modules.professor.model.Professor;
import erp.uniaura.modules.professor.repository.ProfessorRepository;
import erp.uniaura.modules.turma.model.TurmaDisciplina;
import erp.uniaura.modules.turma.repository.TurmaDisciplinaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FrequenciaService {

    private final AulaRepository aulaRepository;
    private final FrequenciaRepository frequenciaRepository;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;
    private final MatriculaRepository matriculaRepository;
    private final ProfessorRepository professorRepository;

    // --- CRIA UMA AULA E AUTOMATICAMENTE GERA REGISTROS DE FREQUÊNCIA PARA TODOS OS ALUNOS ATIVOS DA TURMA ---
    @Transactional
    public AulaResponseDTO criarAula(AulaRequestDTO dto) {
        TurmaDisciplina turmaDisciplina = turmaDisciplinaRepository.findById(dto.getTurmaDisciplinaId())
                .orElseThrow(() -> new ResourceNotFoundException("Vínculo turma/disciplina", dto.getTurmaDisciplinaId()));

        Professor professor;
        if (dto.getProfessorId() != null) {
            professor = professorRepository.findById(dto.getProfessorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Professor", dto.getProfessorId()));
        } else {
            professor = turmaDisciplina.getProfessor();
        }

        Aula aula = Aula.builder()
                .turmaDisciplina(turmaDisciplina)
                .dataAula(dto.getDataAula())
                .conteudoMinistrado(dto.getConteudoMinistrado())
                .professor(professor)
                .build();
        aula = aulaRepository.save(aula);

        List<Matricula> matriculasAtivas = matriculaRepository.findByTurmaIdAndStatus(
                turmaDisciplina.getTurma().getId(), StatusMatricula.ATIVA);

        List<Frequencia> frequencias = new ArrayList<>();
        for (Matricula matricula : matriculasAtivas) {
            frequencias.add(Frequencia.builder()
                    .aula(aula)
                    .aluno(matricula.getAluno())
                    .presente(Boolean.TRUE)
                    .build());
        }
        frequenciaRepository.saveAll(frequencias);

        return toAulaResponse(aula, frequencias.size());
    }

    // --- REGISTRA AS PRESENÇAS/FALTAS DE UMA AULA EM LOTE ---
    @Transactional
    public List<FrequenciaResponseDTO> registrarChamada(Long aulaId, ChamadaRequestDTO dto) {
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Aula", aulaId));

        List<FrequenciaResponseDTO> resultado = new ArrayList<>();
        for (ItemChamadaRequestDTO item : dto.getRegistros()) {
            Frequencia frequencia = frequenciaRepository
                    .findByAulaIdAndAlunoId(aula.getId(), item.getAlunoId())
                    .orElseThrow(() -> new BusinessException(
                            "Aluno " + item.getAlunoId() + " não possui registro de frequência nesta aula."));

            frequencia.setPresente(item.getPresente());
            frequencia.setJustificativa(Boolean.TRUE.equals(item.getPresente()) ? null : item.getJustificativa());

            resultado.add(toFrequenciaResponse(frequenciaRepository.save(frequencia)));
        }
        return resultado;
    }

    // --- LISTA OS REGISTROS DE FREQUÊNCIA DE UMA AULA ---
    @Transactional(readOnly = true)
    public List<FrequenciaResponseDTO> listarFrequenciasDaAula(Long aulaId) {
        aulaRepository.findById(aulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Aula", aulaId));
        return frequenciaRepository.findByAulaId(aulaId)
                .stream()
                .map(this::toFrequenciaResponse)
                .toList();
    }

    // --- CALCULA O PERCENTUAL DE PRESENÇA DE UM ALUNO EM UMA DISCIPLINA ESPECÍFICA ---
    @Transactional(readOnly = true)
    public FrequenciaPercentualDTO calcularPercentualPresencaAluno(Long alunoId, Long disciplinaId) {
        List<Frequencia> frequencias = frequenciaRepository.findByAlunoAndDisciplina(alunoId, disciplinaId);
        if (frequencias.isEmpty()) {
            throw new ResourceNotFoundException("Frequências do aluno na disciplina",
                    alunoId + "/" + disciplinaId);
        }

        Aluno aluno = frequencias.get(0).getAluno();
        var disciplina = frequencias.get(0).getAula().getTurmaDisciplina().getDisciplina();

        return montarPercentual(aluno, disciplina.getId(), disciplina.getNome(), frequencias);
    }

    // --- PERCENTUAL POR DISCIPLINA, AGRUPANDO TODAS AS FREQUÊNCIAS DO ALUNO ---
    @Transactional(readOnly = true)
    public FrequenciaResumoDTO resumoDoAluno(Long alunoId) {
        List<Frequencia> frequencias = frequenciaRepository.findByAlunoId(alunoId);
        if (frequencias.isEmpty()) {
            throw new ResourceNotFoundException("Frequências do aluno", alunoId);
        }

        Aluno aluno = frequencias.get(0).getAluno();

        Map<Long, List<Frequencia>> porDisciplina = new LinkedHashMap<>();
        for (Frequencia f : frequencias) {
            porDisciplina.computeIfAbsent(f.getAula().getTurmaDisciplina().getDisciplina().getId(),
                            k -> new ArrayList<>()).add(f);
        }

        List<FrequenciaPercentualDTO> linhas = porDisciplina.values().stream()
                .map(grupo -> {
                    var disciplina = grupo.get(0).getAula().getTurmaDisciplina().getDisciplina();
                    return montarPercentual(aluno, disciplina.getId(), disciplina.getNome(), grupo);
                })
                .toList();

        return FrequenciaResumoDTO.builder()
                .alunoId(aluno.getId())
                .alunoNome(aluno.getUsuario().getNome())
                .alunoMatriculaRA(aluno.getMatriculaRA())
                .disciplinas(linhas)
                .build();
    }

    // --- CALCULA O PERCENTUAL ---
    private FrequenciaPercentualDTO montarPercentual(Aluno aluno, Long disciplinaId, String disciplinaNome, List<Frequencia> frequencias) {
        long total = frequencias.size();
        long presentes = frequencias.stream().filter(f -> Boolean.TRUE.equals(f.getPresente())).count();
        long faltas = total - presentes;

        BigDecimal percentual = (total == 0)
                ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.valueOf(presentes)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);

        return FrequenciaPercentualDTO.builder()
                .alunoId(aluno.getId())
                .alunoNome(aluno.getUsuario().getNome())
                .disciplinaId(disciplinaId)
                .disciplinaNome(disciplinaNome)
                .totalAulas(total)
                .presencas(presentes)
                .faltas(faltas)
                .percentual(percentual)
                .build();
    }

    // --- CONVERSÃO AULA ---
    private AulaResponseDTO toAulaResponse(Aula aula, int totalAlunos) {
        TurmaDisciplina turmaDisciplina = aula.getTurmaDisciplina();
        return AulaResponseDTO.builder()
                .id(aula.getId())
                .turmaDisciplinaId(turmaDisciplina.getId())
                .turmaId(turmaDisciplina.getTurma().getId())
                .turmaCodigo(turmaDisciplina.getTurma().getCodigo())
                .disciplinaId(turmaDisciplina.getDisciplina().getId())
                .disciplinaNome(turmaDisciplina.getDisciplina().getNome())
                .professorId(aula.getProfessor().getId())
                .professorNome(aula.getProfessor().getUsuario().getNome())
                .dataAula(aula.getDataAula())
                .conteudoMinistrado(aula.getConteudoMinistrado())
                .totalAlunos(totalAlunos)
                .criadaEm(aula.getCriadaEm())
                .build();
    }

    // --- CONVERSÃO FREQUENCIA ---
    private FrequenciaResponseDTO toFrequenciaResponse(Frequencia frequencia) {
        return FrequenciaResponseDTO.builder()
                .id(frequencia.getId())
                .aulaId(frequencia.getAula().getId())
                .dataAula(frequencia.getAula().getDataAula())
                .alunoId(frequencia.getAluno().getId())
                .alunoNome(frequencia.getAluno().getUsuario().getNome())
                .alunoMatriculaRA(frequencia.getAluno().getMatriculaRA())
                .presente(frequencia.getPresente())
                .justificativa(frequencia.getJustificativa())
                .build();
    }
}

