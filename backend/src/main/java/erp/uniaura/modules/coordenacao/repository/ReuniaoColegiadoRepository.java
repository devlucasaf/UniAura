package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.ReuniaoColegiado;
import erp.uniaura.modules.coordenacao.model.StatusReuniao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReuniaoColegiadoRepository extends JpaRepository<ReuniaoColegiado, UUID> {

    @Query("""
            SELECT r FROM ReuniaoColegiado r
             WHERE (:cursoId IS NULL OR r.curso.id = :cursoId)
               AND (:status IS NULL OR r.status = :status)
            """)
    Page<ReuniaoColegiado> buscarComFiltros(@Param("cursoId") UUID cursoId,
                                            @Param("status") StatusReuniao status,
                                            Pageable pageable);
}
