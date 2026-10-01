package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.AvaliacaoPlanoEnsino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoPlanoEnsinoRepository extends JpaRepository<AvaliacaoPlanoEnsino, Long> {

    List<AvaliacaoPlanoEnsino> findByPlanoEnsinoIdOrderByCriadoEmAsc(Long planoEnsinoId);
}
