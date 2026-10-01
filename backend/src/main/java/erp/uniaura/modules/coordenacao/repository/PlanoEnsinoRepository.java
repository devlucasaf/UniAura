package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.PlanoEnsino;
import erp.uniaura.modules.coordenacao.model.StatusPlanoEnsino;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlanoEnsinoRepository extends JpaRepository<PlanoEnsino, Long> {

    Optional<PlanoEnsino> findByTurmaDisciplinaId(Long turmaDisciplinaId);

    boolean existsByTurmaDisciplinaId(Long turmaDisciplinaId);

    Page<PlanoEnsino> findByProfessorId(Long professorId, Pageable pageable);

    // --- FILA DA COORDENAÇÃO, COM FILTROS OPCIONAIS DE STATUS, CURSO E PERÍODO LETIVO ---
    @Query("""
            SELECT p FROM PlanoEnsino p
             WHERE (:status IS NULL OR p.status = :status)
               AND (:cursoId IS NULL OR p.turmaDisciplina.turma.curso.id = :cursoId)
               AND (:periodoLetivo IS NULL OR p.turmaDisciplina.turma.periodoLetivo = :periodoLetivo)
            """)
    Page<PlanoEnsino> buscarComFiltros(@Param("status") StatusPlanoEnsino status,
                                       @Param("cursoId") Long cursoId,
                                       @Param("periodoLetivo") String periodoLetivo,
                                       Pageable pageable);
}
