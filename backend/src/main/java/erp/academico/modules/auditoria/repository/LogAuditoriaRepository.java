package erp.academico.modules.auditoria.repository;

import erp.academico.modules.auditoria.model.LogAuditoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, UUID> {

    Page<LogAuditoria> findByEntidadeIgnoreCase(String entidade, Pageable pageable);

    Page<LogAuditoria> findByUsuarioId(UUID usuarioId, Pageable pageable);
}
