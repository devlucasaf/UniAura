package erp.uniaura.modules.professor.repository;

import erp.uniaura.modules.professor.model.ProfessorDisciplina;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorDisciplinaRepository extends JpaRepository<ProfessorDisciplina, Long> {

    List<ProfessorDisciplina> findByProfessorId(Long professorId);

    Optional<ProfessorDisciplina> findByProfessorIdAndDisciplinaId(Long professorId, Long disciplinaId);

    boolean existsByProfessorIdAndDisciplinaId(Long professorId, Long disciplinaId);
}

