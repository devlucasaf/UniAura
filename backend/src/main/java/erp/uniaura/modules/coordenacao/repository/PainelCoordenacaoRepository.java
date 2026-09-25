package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.dto.MediaDisciplinaProjecao;
import erp.uniaura.modules.turma.model.Turma;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// --- CONSULTAS AGREGADAS QUE ALIMENTAM O PAINEL DA COORDENAÇÃO ---
// --- OS FILTROS DE CURSO E PERÍODO LETIVO SÃO OPCIONAIS: QUANDO NULOS, A CONTAGEM É GLOBAL ---
@Repository
public interface PainelCoordenacaoRepository extends JpaRepository<Turma, UUID> {

    // --- ALUNOS DISTINTOS POR STATUS DE MATRÍCULA ---
    @Query("""
            SELECT m.status, COUNT(DISTINCT m.aluno.id)
              FROM Matricula m
             WHERE (:cursoId IS NULL OR m.turma.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR m.turma.periodoLetivo = :periodoLetivo)
             GROUP BY m.status
            """)
    List<Object[]> contarAlunosPorStatusMatricula(@Param("cursoId") UUID cursoId,
                                                  @Param("periodoLetivo") String periodoLetivo);

    @Query("""
            SELECT COUNT(t)
              FROM Turma t
             WHERE (:cursoId IS NULL OR t.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR t.periodoLetivo = :periodoLetivo)
            """)
    long contarTurmas(@Param("cursoId") UUID cursoId,
                      @Param("periodoLetivo") String periodoLetivo);

    @Query("""
            SELECT COUNT(t)
              FROM Turma t
             WHERE t.ativa = true
               AND (:cursoId IS NULL OR t.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR t.periodoLetivo = :periodoLetivo)
            """)
    long contarTurmasAtivas(@Param("cursoId") UUID cursoId,
                            @Param("periodoLetivo") String periodoLetivo);

    // --- TURMAS ATIVAS AINDA SEM PROFESSOR REGENTE DEFINIDO ---
    @Query("""
            SELECT t
              FROM Turma t
             WHERE t.ativa = true
               AND t.professorRegente IS NULL
               AND (:cursoId IS NULL OR t.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR t.periodoLetivo = :periodoLetivo)
             ORDER BY t.codigo
            """)
    List<Turma> buscarTurmasSemProfessorRegente(@Param("cursoId") UUID cursoId,
                                                @Param("periodoLetivo") String periodoLetivo);

    // --- SOMA DAS CAPACIDADES E DAS MATRÍCULAS ATIVAS, PARA A TAXA DE OCUPAÇÃO ---
    @Query("""
            SELECT COALESCE(SUM(t.capacidadeMaxima), 0)
              FROM Turma t
             WHERE t.ativa = true
               AND (:cursoId IS NULL OR t.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR t.periodoLetivo = :periodoLetivo)
            """)
    long somarCapacidadeTurmasAtivas(@Param("cursoId") UUID cursoId,
                                     @Param("periodoLetivo") String periodoLetivo);

    @Query("""
            SELECT COUNT(m)
              FROM Matricula m
             WHERE m.status = erp.academico.modules.matricula.model.StatusMatricula.ATIVA
               AND m.turma.ativa = true
               AND (:cursoId IS NULL OR m.turma.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR m.turma.periodoLetivo = :periodoLetivo)
            """)
    long contarMatriculasAtivasEmTurmasAtivas(@Param("cursoId") UUID cursoId,
                                              @Param("periodoLetivo") String periodoLetivo);

    @Query("""
            SELECT COUNT(m)
              FROM Matricula m
             WHERE m.status = erp.academico.modules.matricula.model.StatusMatricula.ATIVA
               AND m.turma.id = :turmaId
            """)
    long contarMatriculasAtivasDaTurma(@Param("turmaId") UUID turmaId);

    // --- UMA LINHA POR ALUNO/DISCIPLINA COM AS SOMAS NECESSÁRIAS PARA A MÉDIA PONDERADA ---
    @Query("""
            SELECT new erp.academico.modules.coordenacao.dto.MediaDisciplinaProjecao(
                       n.aluno.id,
                       n.turmaDisciplina.id,
                       SUM(n.valor * n.peso),
                       SUM(n.peso))
              FROM Nota n
             WHERE (:cursoId IS NULL OR n.turmaDisciplina.turma.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR n.turmaDisciplina.turma.periodoLetivo = :periodoLetivo)
             GROUP BY n.aluno.id, n.turmaDisciplina.id
            """)
    List<MediaDisciplinaProjecao> buscarMediasPorAlunoDisciplina(@Param("cursoId") UUID cursoId,
                                                                 @Param("periodoLetivo") String periodoLetivo);

    // --- TOTAL DE REGISTROS DE CHAMADA E QUANTOS FORAM PRESENÇA ---
    @Query("""
            SELECT COUNT(f), COALESCE(SUM(CASE WHEN f.presente = true THEN 1 ELSE 0 END), 0)
              FROM Frequencia f
             WHERE (:cursoId IS NULL OR f.aula.turmaDisciplina.turma.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR f.aula.turmaDisciplina.turma.periodoLetivo = :periodoLetivo)
            """)
    List<Object[]> resumirFrequencia(@Param("cursoId") UUID cursoId,
                                     @Param("periodoLetivo") String periodoLetivo);

    // --- PENDÊNCIAS: PROCESSO E MANIFESTAÇÃO NÃO POSSUEM VÍNCULO COM CURSO, ENTÃO A CONTAGEM É GLOBAL ---
    @Query("""
            SELECT COUNT(p)
              FROM Processo p
             WHERE p.status IN (erp.academico.modules.processo.model.StatusProcesso.ABERTO,
                                erp.academico.modules.processo.model.StatusProcesso.EM_ANALISE,
                                erp.academico.modules.processo.model.StatusProcesso.AGUARDANDO_ALUNO)
            """)
    long contarProcessosPendentes();

    @Query("""
            SELECT COUNT(m)
              FROM Manifestacao m
             WHERE m.status IN (erp.academico.modules.ouvidoria.model.StatusManifestacao.ABERTA,
                                erp.academico.modules.ouvidoria.model.StatusManifestacao.EM_APURACAO)
            """)
    long contarManifestacoesPendentes();
}
