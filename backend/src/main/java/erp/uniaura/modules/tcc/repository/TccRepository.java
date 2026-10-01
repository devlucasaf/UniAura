package erp.uniaura.modules.tcc.repository;

import erp.uniaura.modules.tcc.model.StatusTcc;
import erp.uniaura.modules.tcc.model.Tcc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TccRepository extends JpaRepository<Tcc, Long> {

    Page<Tcc> findByAlunoId(Long alunoId, Pageable pageable);

    Page<Tcc> findByStatus(StatusTcc status, Pageable pageable);

    Page<Tcc> findByProfessorOrientadorId(Long professorId, Pageable pageable);

    Optional<Tcc> findFirstByAlunoIdAndStatusNotIn(Long alunoId, List<StatusTcc> statusFinalizados);
}
