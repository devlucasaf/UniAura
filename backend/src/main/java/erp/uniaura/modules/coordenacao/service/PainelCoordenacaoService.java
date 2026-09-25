package erp.uniaura.modules.coordenacao.service;

import erp.uniaura.exception.ResourceNotFoundException;
import erp.uniaura.modules.coordenacao.dto.MediaDisciplinaProjecao;
import erp.uniaura.modules.coordenacao.dto.PainelCoordenacaoResponseDTO;
import erp.uniaura.modules.coordenacao.dto.TurmaSemRegenteDTO;
import erp.uniaura.modules.coordenacao.repository.PainelCoordenacaoRepository;
import erp.uniaura.modules.curso.model.Curso;
import erp.uniaura.modules.curso.repository.CursoRepository;
import erp.uniaura.modules.turma.model.Turma;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PainelCoordenacaoService {

    // --- O SISTEMA NÃO POSSUÍA UMA REGRA DE APROVAÇÃO; ESTE É O PATAMAR ADOTADO AQUI ---
    private static final BigDecimal MEDIA_APROVACAO = new BigDecimal("6.00");

    private static final String STATUS_MATRICULA_ATIVA = "ATIVA";

    private static final int ESCALA = 2;

    private static final BigDecimal CEM = new BigDecimal("100");

    private final PainelCoordenacaoRepository painelRepository;
    private final CursoRepository cursoRepository;

    // --- CONSOLIDA OS INDICADORES DO CURSO E DO PERÍODO LETIVO INFORMADOS ---
    @Transactional(readOnly = true)
    public PainelCoordenacaoResponseDTO consolidar(UUID cursoId, String periodoLetivo) {
        String periodo = (periodoLetivo == null || periodoLetivo.isBlank()) ? null : periodoLetivo;
        Curso curso = cursoId == null ? null : buscarCurso(cursoId);

        Map<String, Long> alunosPorStatus = contarAlunosPorStatus(cursoId, periodo);
        List<TurmaSemRegenteDTO> semRegente = buscarTurmasSemRegente(cursoId, periodo);
        Desempenho desempenho = calcularDesempenho(cursoId, periodo);

        return PainelCoordenacaoResponseDTO.builder()
                .cursoId(cursoId)
                .cursoNome(curso == null ? null : curso.getNome())
                .periodoLetivo(periodo)

                .alunosPorStatusMatricula(alunosPorStatus)
                .totalAlunosAtivos(alunosPorStatus.getOrDefault(STATUS_MATRICULA_ATIVA, 0L))

                .totalTurmas(painelRepository.contarTurmas(cursoId, periodo))
                .turmasAtivas(painelRepository.contarTurmasAtivas(cursoId, periodo))
                .turmasSemProfessorRegente((long) semRegente.size())
                .ocupacaoMediaPercentual(calcularOcupacao(cursoId, periodo))

                .totalResultadosAvaliados(desempenho.total())
                .totalAprovacoes(desempenho.aprovacoes())
                .totalReprovacoes(desempenho.total() - desempenho.aprovacoes())
                .taxaAprovacaoPercentual(percentual(desempenho.aprovacoes(), desempenho.total()))
                .mediaGeral(desempenho.mediaGeral())

                .frequenciaMediaPercentual(calcularFrequenciaMedia(cursoId, periodo))

                .processosPendentes(painelRepository.contarProcessosPendentes())
                .manifestacoesPendentes(painelRepository.contarManifestacoesPendentes())

                .turmasQueExigemAtencao(semRegente)
                .build();
    }

    // --- HELPERS ---

    // --- RESULTADO INTERMEDIÁRIO DA APURAÇÃO DE NOTAS ---
    private record Desempenho(long total, long aprovacoes, BigDecimal mediaGeral) {
    }

    // --- BUSCA O CURSO PELO IDENTIFICADOR OU LANÇA UMA EXCEÇÃO CASO ELE NÃO SEJA ENCONTRADO ---
    private Curso buscarCurso(UUID id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso", id));
    }

    // --- CONVERTE AS LINHAS AGRUPADAS EM UM MAPA DE STATUS PARA QUANTIDADE ---
    private Map<String, Long> contarAlunosPorStatus(UUID cursoId, String periodoLetivo) {
        Map<String, Long> resultado = new LinkedHashMap<>();
        for (Object[] linha : painelRepository.contarAlunosPorStatusMatricula(cursoId, periodoLetivo)) {
            resultado.put(String.valueOf(linha[0]), ((Number) linha[1]).longValue());
        }
        return resultado;
    }

    // --- MONTA A LISTA DE TURMAS ATIVAS QUE AINDA NÃO TÊM REGENTE ---
    private List<TurmaSemRegenteDTO> buscarTurmasSemRegente(UUID cursoId, String periodoLetivo) {
        List<Turma> turmas = painelRepository.buscarTurmasSemProfessorRegente(cursoId, periodoLetivo);

        return turmas.stream()
                .map(t -> TurmaSemRegenteDTO.builder()
                        .turmaId(t.getId())
                        .turmaCodigo(t.getCodigo())
                        .serie(t.getSerie())
                        .turno(t.getTurno())
                        .periodoLetivo(t.getPeriodoLetivo())
                        .capacidadeMaxima(t.getCapacidadeMaxima())
                        .matriculasAtivas(painelRepository.contarMatriculasAtivasDaTurma(t.getId()))
                        .build())
                .toList();
    }

    // --- PERCENTUAL DE VAGAS OCUPADAS NAS TURMAS ATIVAS ---
    private BigDecimal calcularOcupacao(UUID cursoId, String periodoLetivo) {
        long capacidade = painelRepository.somarCapacidadeTurmasAtivas(cursoId, periodoLetivo);
        long ocupadas = painelRepository.contarMatriculasAtivasEmTurmasAtivas(cursoId, periodoLetivo);
        return percentual(ocupadas, capacidade);
    }

    // --- APURA APROVAÇÕES E MÉDIA GERAL A PARTIR DAS MÉDIAS PONDERADAS DE CADA ALUNO EM CADA DISCIPLINA ---
    private Desempenho calcularDesempenho(UUID cursoId, String periodoLetivo) {
        List<MediaDisciplinaProjecao> medias =
                painelRepository.buscarMediasPorAlunoDisciplina(cursoId, periodoLetivo);

        if (medias.isEmpty()) {
            return new Desempenho(0L, 0L, zero());
        }

        long aprovacoes = 0;
        BigDecimal soma = BigDecimal.ZERO;

        for (MediaDisciplinaProjecao projecao : medias) {
            BigDecimal media = projecao.mediaPonderada();
            soma = soma.add(media);
            if (media.compareTo(MEDIA_APROVACAO) >= 0) {
                aprovacoes++;
            }
        }

        BigDecimal mediaGeral = soma.divide(BigDecimal.valueOf(medias.size()), ESCALA, RoundingMode.HALF_UP);
        return new Desempenho(medias.size(), aprovacoes, mediaGeral);
    }

    // --- PERCENTUAL DE PRESENÇAS SOBRE O TOTAL DE REGISTROS DE CHAMADA ---
    private BigDecimal calcularFrequenciaMedia(UUID cursoId, String periodoLetivo) {
        List<Object[]> linhas = painelRepository.resumirFrequencia(cursoId, periodoLetivo);
        if (linhas.isEmpty()) {
            return zero();
        }

        Object[] linha = linhas.get(0);
        long total = ((Number) linha[0]).longValue();
        long presentes = ((Number) linha[1]).longValue();
        return percentual(presentes, total);
    }

    // --- DIVIDE COM PROTEÇÃO CONTRA DENOMINADOR ZERO E DEVOLVE O VALOR EM PERCENTUAL ---
    private BigDecimal percentual(long parte, long total) {
        if (total <= 0) {
            return zero();
        }
        return BigDecimal.valueOf(parte)
                .multiply(CEM)
                .divide(BigDecimal.valueOf(total), ESCALA, RoundingMode.HALF_UP);
    }

    private BigDecimal zero() {
        return BigDecimal.ZERO.setScale(ESCALA, RoundingMode.HALF_UP);
    }
}
