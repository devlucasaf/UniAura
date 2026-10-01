package erp.uniaura.modules.estagio.repository;

import erp.uniaura.modules.estagio.model.RelatorioEstagio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioEstagioRepository extends JpaRepository<RelatorioEstagio, Long> {

    Page<RelatorioEstagio> findByEstagioId(Long estagioId, Pageable pageable);

    boolean existsByEstagioIdAndPeriodoReferencia(Long estagioId, String periodoReferencia);
}
