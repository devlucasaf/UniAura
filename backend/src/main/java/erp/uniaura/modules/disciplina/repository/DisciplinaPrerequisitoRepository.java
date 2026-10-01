package erp.uniaura.modules.disciplina.repository;

import erp.uniaura.modules.disciplina.model.DisciplinaPrerequisito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaPrerequisitoRepository extends JpaRepository<DisciplinaPrerequisito, Long> {

    List<DisciplinaPrerequisito> findByDisciplinaId(Long disciplinaId);

    Optional<DisciplinaPrerequisito> findByDisciplinaIdAndPrerequisitoId(Long disciplinaId, Long prerequisitoId);

    boolean existsByDisciplinaIdAndPrerequisitoId(Long disciplinaId, Long prerequisitoId);
}

