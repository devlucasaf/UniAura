package erp.uniaura.modules.avaliacao.repository;

import erp.uniaura.modules.avaliacao.model.AvaliacaoDocente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoDocenteRepository extends JpaRepository<AvaliacaoDocente, Long> {

    boolean existsByAlunoIdAndTurmaDisciplinaId(Long alunoId, Long turmaDisciplinaId);

    List<AvaliacaoDocente> findByTurmaDisciplinaId(Long turmaDisciplinaId);

    @Query("""
            SELECT COUNT(a), COALESCE(AVG(a.notaDidatica), 0), COALESCE(AVG(a.notaPontualidade), 0),
                   COALESCE(AVG(a.notaDisponibilidade), 0)
              FROM AvaliacaoDocente a
             WHERE a.turmaDisciplina.id = :turmaDisciplinaId
            """)
    Object[] resumoPorTurmaDisciplina(@Param("turmaDisciplinaId") Long turmaDisciplinaId);

    @Query("""
            SELECT COUNT(a), COALESCE(AVG(a.notaDidatica), 0), COALESCE(AVG(a.notaPontualidade), 0),
                   COALESCE(AVG(a.notaDisponibilidade), 0)
              FROM AvaliacaoDocente a
             WHERE a.turmaDisciplina.professor.id = :professorId
            """)
    Object[] resumoPorProfessor(@Param("professorId") Long professorId);
}
