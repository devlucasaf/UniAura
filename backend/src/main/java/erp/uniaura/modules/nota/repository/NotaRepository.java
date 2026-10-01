package erp.uniaura.modules.nota.repository;

import erp.uniaura.modules.nota.model.Nota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByTurmaDisciplina_TurmaIdAndTurmaDisciplina_DisciplinaId(Long turmaId, Long disciplinaId);

    List<Nota> findByAlunoId(Long alunoId);

    List<Nota> findByAlunoIdAndTurmaDisciplina_TurmaPeriodoLetivo(Long alunoId, String periodoLetivo);
}

