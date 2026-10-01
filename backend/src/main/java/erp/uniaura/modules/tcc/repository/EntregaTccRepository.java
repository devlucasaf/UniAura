package erp.uniaura.modules.tcc.repository;

import erp.uniaura.modules.tcc.model.EntregaTcc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaTccRepository extends JpaRepository<EntregaTcc, Long> {

    Page<EntregaTcc> findByTccId(Long tccId, Pageable pageable);
}
