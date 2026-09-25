package erp.uniaura.modules.processo.repository;

import erp.uniaura.modules.processo.model.Processo;
import erp.uniaura.modules.processo.model.StatusProcesso;
import erp.uniaura.modules.processo.model.TipoProcesso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, UUID> {

    Optional<Processo> findByProtocolo(String protocolo);

    boolean existsByProtocolo(String protocolo);

    Page<Processo> findByAlunoId(UUID alunoId, Pageable pageable);

    Page<Processo> findByAlunoIdAndStatus(UUID alunoId, StatusProcesso status, Pageable pageable);

    // --- LISTAGEM DA SECRETARIA, COM FILTROS OPCIONAIS DE STATUS E TIPO ---
    @Query("""
            SELECT p FROM Processo p
             WHERE (:status IS NULL OR p.status = :status)
               AND (:tipo IS NULL OR p.tipo = :tipo)
            """)
    Page<Processo> buscarComFiltros(@Param("status") StatusProcesso status,
                                    @Param("tipo") TipoProcesso tipo,
                                    Pageable pageable);
}
