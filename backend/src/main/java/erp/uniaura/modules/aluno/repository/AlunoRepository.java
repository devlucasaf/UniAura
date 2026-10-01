package erp.uniaura.modules.aluno.repository;

import erp.uniaura.modules.aluno.model.Aluno;
import erp.uniaura.modules.aluno.model.StatusAluno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByMatriculaRA(String matriculaRA);

    boolean existsByMatriculaRA(String matriculaRA);

    Optional<Aluno> findByUsuarioId(Long usuarioId);

    Page<Aluno> findByStatus(StatusAluno status, Pageable pageable);

    Page<Aluno> findByTurmaAtualId(Long turmaAtualId, Pageable pageable);
}
