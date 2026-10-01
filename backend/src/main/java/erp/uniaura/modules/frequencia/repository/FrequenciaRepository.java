package erp.uniaura.modules.frequencia.repository;

import erp.uniaura.modules.frequencia.model.Frequencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {

    List<Frequencia> findByAulaId(Long aulaId);

    Optional<Frequencia> findByAulaIdAndAlunoId(Long aulaId, Long alunoId);

    @Query("""
            select f from Frequencia f
            where f.aluno.id = :alunoId
              and f.aula.turmaDisciplina.disciplina.id = :disciplinaId
            """)
    List<Frequencia> findByAlunoAndDisciplina(@Param("alunoId") Long alunoId,
                                              @Param("disciplinaId") Long disciplinaId);

    List<Frequencia> findByAlunoId(Long alunoId);
}

