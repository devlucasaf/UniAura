package erp.uniaura.modules.financeiro.repository;

import erp.uniaura.modules.financeiro.model.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByMensalidadeId(Long mensalidadeId);
}
