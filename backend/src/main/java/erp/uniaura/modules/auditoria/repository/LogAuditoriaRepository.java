package erp.uniaura.modules.auditoria.repository;

import erp.uniaura.modules.auditoria.model.LogAuditoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {

    Page<LogAuditoria> findByEntidadeIgnoreCase(String entidade, Pageable pageable);

    Page<LogAuditoria> findByUsuarioId(Long usuarioId, Pageable pageable);
}
