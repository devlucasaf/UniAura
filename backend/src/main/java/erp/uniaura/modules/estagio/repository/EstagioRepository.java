package erp.uniaura.modules.estagio.repository;

import erp.uniaura.modules.estagio.model.Estagio;
import erp.uniaura.modules.estagio.model.StatusEstagio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstagioRepository extends JpaRepository<Estagio, Long> {

    Page<Estagio> findByAlunoId(Long alunoId, Pageable pageable);

    Page<Estagio> findByStatus(StatusEstagio status, Pageable pageable);

    Page<Estagio> findByAlunoIdAndStatus(Long alunoId, StatusEstagio status, Pageable pageable);

    Optional<Estagio> findFirstByAlunoIdAndStatus(Long alunoId, StatusEstagio status);
}
