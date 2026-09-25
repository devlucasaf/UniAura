package erp.uniaura.modules.ouvidoria.repository;

import erp.uniaura.modules.ouvidoria.model.Manifestacao;
import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;
import erp.uniaura.modules.ouvidoria.model.TipoManifestacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManifestacaoRepository extends JpaRepository<Manifestacao, UUID> {

    Optional<Manifestacao> findByProtocolo(String protocolo);

    boolean existsByProtocolo(String protocolo);

    Page<Manifestacao> findByAutorId(UUID autorId, Pageable pageable);

    Page<Manifestacao> findByAutorIdAndStatus(UUID autorId, StatusManifestacao status, Pageable pageable);

    // --- LISTAGEM DA OUVIDORIA, COM FILTROS OPCIONAIS DE STATUS E TIPO ---
    @Query("""
            SELECT m FROM Manifestacao m
             WHERE (:status IS NULL OR m.status = :status)
               AND (:tipo IS NULL OR m.tipo = :tipo)
            """)
    Page<Manifestacao> buscarComFiltros(@Param("status") StatusManifestacao status,
                                        @Param("tipo") TipoManifestacao tipo,
                                        Pageable pageable);
}
