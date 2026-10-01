package erp.uniaura.modules.disciplina.repository;

import erp.uniaura.modules.disciplina.model.Disciplina;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    Optional<Disciplina> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    Page<Disciplina> findByCursoId(Long cursoId, Pageable pageable);
}

