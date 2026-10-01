package erp.uniaura.modules.matricula.repository;

import erp.uniaura.modules.matricula.model.Matricula;
import erp.uniaura.modules.matricula.model.StatusMatricula;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Page<Matricula> findByAlunoId(Long alunoId, Pageable pageable);

    boolean existsByAlunoIdAndTurmaPeriodoLetivoAndStatus(Long alunoId, String periodoLetivo, StatusMatricula status);

    long countByTurmaIdAndStatus(Long turmaId, StatusMatricula status);

    // --- MATRÍCULAS DE UMA TURMA EM UM STATUS ---
    List<Matricula> findByTurmaIdAndStatus(Long turmaId, StatusMatricula status);

    // --- VERIFICA SE O ALUNO ESTÁ COM UMA MATRÍCULA NO STATUS INFORMADO NA TURMA ---
    boolean existsByAlunoIdAndTurmaIdAndStatus(Long alunoId, Long turmaId, StatusMatricula status);
}
