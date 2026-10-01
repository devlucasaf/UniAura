package erp.uniaura.modules.turma.repository;

import erp.uniaura.modules.turma.model.Turma;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    Page<Turma> findByCursoId(Long cursoId, Pageable pageable);

    Page<Turma> findByPeriodoLetivo(String periodoLetivo, Pageable pageable);
}
