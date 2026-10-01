package erp.uniaura.modules.curso.repository;

import erp.uniaura.modules.curso.model.Curso;
import erp.uniaura.modules.curso.model.NivelCurso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByNivel(NivelCurso nivel, Pageable pageable);

    Page<Curso> findByAtivo(Boolean ativo, Pageable pageable);
}

