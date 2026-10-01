package erp.uniaura.modules.turma.repository;

import erp.uniaura.modules.turma.model.TurmaDisciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

    List<TurmaDisciplina> findByTurmaId(Long turmaId);

    List<TurmaDisciplina> findByProfessorId(Long professorId);

    boolean existsByTurmaIdAndDisciplinaId(Long turmaId, Long disciplinaId);

    Optional<TurmaDisciplina> findByTurmaIdAndDisciplinaId(Long turmaId, Long disciplinaId);
}

