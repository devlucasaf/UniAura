package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.AvaliacaoPlanoEnsino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoPlanoEnsinoRepository extends JpaRepository<AvaliacaoPlanoEnsino, UUID> {

    List<AvaliacaoPlanoEnsino> findByPlanoEnsinoIdOrderByCriadoEmAsc(UUID planoEnsinoId);
}
