package erp.uniaura.services.web.coordenacao.consultarPainelDaCoordenacao.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.StatusManifestacao;
import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.persistence.entities.StatusProcesso;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import jakarta.persistence.Query;

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
public class ConsultarPainelDaCoordenacaoServiceV1 extends BaseService {

    private static final BigDecimal MEDIA_APROVACAO       = new BigDecimal("6.00");
    private static final String     STATUS_MATRICULA_ATIVA = "ATIVA";
    private static final int        ESCALA                 = 2;
    private static final BigDecimal CEM                    = new BigDecimal("100");

    private final BuscarCursoComponent buscarCursoComponent;

    @Transactional(readOnly = true)
    public ConsultarPainelDaCoordenacaoRetornoV1 consultar(ConsultarPainelDaCoordenacaoParamsV1 params) {
        Curso curso = null;

        if (params.getCursoUid() != null) {
            curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());
        }

        String periodo = null;

        if (params.getPeriodoLetivo() != null && !params.getPeriodoLetivo().isBlank()) {
            periodo = params.getPeriodoLetivo();
        }

        Map<String, Long> alunosPorStatus = contarAlunosPorStatus(curso, periodo);
        List<ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1> semRegente = buscarTurmasSemRegente(curso, periodo);

        long totalAvaliado = 0;
        long aprovacoes = 0;
        BigDecimal somaDasMedias = BigDecimal.ZERO;

        for (Object[] linha : buscarSomasDasNotas(curso, periodo)) {
            BigDecimal media = mediaPonderada((BigDecimal) linha[0], (BigDecimal) linha[1]);

            somaDasMedias = somaDasMedias.add(media);
            totalAvaliado++;

            if (media.compareTo(MEDIA_APROVACAO) >= 0) {
                aprovacoes++;
            }
        }

        BigDecimal mediaGeral = zero();

        if (totalAvaliado > 0) {
            mediaGeral = somaDasMedias.divide(BigDecimal.valueOf(totalAvaliado), ESCALA, RoundingMode.HALF_UP);
        }

        ConsultarPainelDaCoordenacaoRetornoV1 retorno = new ConsultarPainelDaCoordenacaoRetornoV1();

        if (curso != null) {
            retorno.setCursoUid(curso.getUid());
            retorno.setCursoNome(curso.getNome());
        }

        retorno.setPeriodoLetivo(periodo);
        retorno.setAlunosPorStatusMatricula(alunosPorStatus);
        retorno.setTotalAlunosAtivos(alunosPorStatus.getOrDefault(STATUS_MATRICULA_ATIVA, 0L));
        retorno.setTotalTurmas(contar("select count(t) from Turma t where 1 = 1", "t", curso, periodo));
        retorno.setTurmasAtivas(contar("select count(t) from Turma t where t.ativa = true", "t", curso, periodo));
        retorno.setTurmasSemProfessorRegente((long) semRegente.size());
        retorno.setOcupacaoMediaPercentual(calcularOcupacao(curso, periodo));
        retorno.setTotalResultadosAvaliados(totalAvaliado);
        retorno.setTotalAprovacoes(aprovacoes);
        retorno.setTotalReprovacoes(totalAvaliado - aprovacoes);
        retorno.setTaxaAprovacaoPercentual(percentual(aprovacoes, totalAvaliado));
        retorno.setMediaGeral(mediaGeral);
        retorno.setFrequenciaMediaPercentual(calcularFrequenciaMedia(curso, periodo));
        retorno.setProcessosPendentes(contarProcessosPendentes());
        retorno.setManifestacoesPendentes(contarManifestacoesPendentes());
        retorno.setTurmasQueExigemAtencao(semRegente);

        return retorno;
    }

    // --- ALUNOS DISTINTOS POR STATUS DE MATRÍCULA ---
    private Map<String, Long> contarAlunosPorStatus(Curso curso, String periodo) {
        Query query = criarConsulta("select m.status, count(distinct m.aluno) from Matricula m where 1 = 1",
                "m.turma", " group by m.status", curso, periodo);

        Map<String, Long> resultado = new LinkedHashMap<>();

        for (Object linha : query.getResultList()) {
            Object[] colunas = (Object[]) linha;

            resultado.put(String.valueOf(colunas[0]), ((Number) colunas[1]).longValue());
        }

        return resultado;
    }

    // --- TURMAS ATIVAS AINDA SEM PROFESSOR REGENTE DEFINIDO ---
    private List<ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1> buscarTurmasSemRegente(Curso curso, String periodo) {
        Query query = criarConsulta("select t from Turma t where t.ativa = true and t.professorRegente is null",
                "t", " order by t.codigo", curso, periodo);

        List<ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1> lista = new ArrayList<>();

        for (Object linha : query.getResultList()) {
            Turma turma = (Turma) linha;

            ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1 item = new ConsultarPainelDaCoordenacaoTurmaSemRegenteRetornoV1();

            item.setTurmaUid(turma.getUid());
            item.setTurmaCodigo(turma.getCodigo());
            item.setSerie(turma.getSerie());
            item.setTurno(turma.getTurno());
            item.setPeriodoLetivo(turma.getPeriodoLetivo());
            item.setCapacidadeMaxima(turma.getCapacidadeMaxima());
            item.setMatriculasAtivas(contarMatriculasAtivasDaTurma(turma));

            lista.add(item);
        }

        return lista;
    }

    private Long contarMatriculasAtivasDaTurma(Turma turma) {
        return em()
                .createQuery("select count(m) from Matricula m where m.status = :status and m.turma = :turma", Long.class)
                .setParameter("status", StatusMatricula.ATIVA)
                .setParameter("turma", turma)
                .getSingleResult();
    }

    // --- PERCENTUAL DE VAGAS OCUPADAS NAS TURMAS ATIVAS ---
    private BigDecimal calcularOcupacao(Curso curso, String periodo) {
        long capacidade = contar("select coalesce(sum(t.capacidadeMaxima), 0) from Turma t where t.ativa = true", "t", curso, periodo);

        Query query = criarConsulta("select count(m) from Matricula m where m.status = :status and m.turma.ativa = true",
                "m.turma", "", curso, periodo);
        query.setParameter("status", StatusMatricula.ATIVA);

        long ocupadas = ((Number) query.getSingleResult()).longValue();

        return percentual(ocupadas, capacidade);
    }

    // --- UMA LINHA POR ALUNO E DISCIPLINA COM A SOMA PONDERADA E A SOMA DOS PESOS ---
    private List<Object[]> buscarSomasDasNotas(Curso curso, String periodo) {
        Query query = criarConsulta("select sum(n.valor * n.peso), sum(n.peso) from Nota n where 1 = 1",
                "n.turmaDisciplina.turma", " group by n.aluno, n.turmaDisciplina", curso, periodo);

        List<Object[]> linhas = new ArrayList<>();

        for (Object linha : query.getResultList()) {
            linhas.add((Object[]) linha);
        }

        return linhas;
    }

    // --- PERCENTUAL DE PRESENÇAS SOBRE O TOTAL DE REGISTROS DE CHAMADA ---
    private BigDecimal calcularFrequenciaMedia(Curso curso, String periodo) {
        Query query = criarConsulta("select count(f), coalesce(sum(case when f.presente = true then 1 else 0 end), 0) from Frequencia f where 1 = 1",
                "f.aula.turmaDisciplina.turma", "", curso, periodo);

        Object[] linha = (Object[]) query.getSingleResult();

        return percentual(((Number) linha[1]).longValue(), ((Number) linha[0]).longValue());
    }

    // --- PROCESSO E MANIFESTAÇÃO NÃO TÊM VÍNCULO COM CURSO, ENTÃO A CONTAGEM É GLOBAL ---
    private Long contarProcessosPendentes() {
        return em()
                .createQuery("select count(p) from Processo p where p.status in :status", Long.class)
                .setParameter("status", List.of(StatusProcesso.ABERTO, StatusProcesso.EM_ANALISE, StatusProcesso.AGUARDANDO_ALUNO))
                .getSingleResult();
    }

    private Long contarManifestacoesPendentes() {
        return em()
                .createQuery("select count(m) from Manifestacao m where m.status in :status", Long.class)
                .setParameter("status", List.of(StatusManifestacao.ABERTA, StatusManifestacao.EM_APURACAO))
                .getSingleResult();
    }

    // --- ACRESCENTA OS FILTROS OPCIONAIS DE CURSO E PERÍODO À CONSULTA, SOBRE O CAMINHO ATÉ A TURMA ---
    private Query criarConsulta(String base, String caminhoDaTurma, String sufixo, Curso curso, String periodo) {
        StringBuilder jpql = new StringBuilder(base);

        if (curso != null) {
            jpql.append(" and ").append(caminhoDaTurma).append(".curso = :curso");
        }

        if (periodo != null) {
            jpql.append(" and ").append(caminhoDaTurma).append(".periodoLetivo = :periodo");
        }

        jpql.append(sufixo);

        Query query = em().createQuery(jpql.toString());

        if (curso != null) {
            query.setParameter("curso", curso);
        }

        if (periodo != null) {
            query.setParameter("periodo", periodo);
        }

        return query;
    }

    private long contar(String base, String caminhoDaTurma, Curso curso, String periodo) {
        return ((Number) criarConsulta(base, caminhoDaTurma, "", curso, periodo).getSingleResult()).longValue();
    }

    private BigDecimal mediaPonderada(BigDecimal somaPonderada, BigDecimal somaPesos) {
        if (somaPesos == null || somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return zero();
        }

        return somaPonderada.divide(somaPesos, ESCALA, RoundingMode.HALF_UP);
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
