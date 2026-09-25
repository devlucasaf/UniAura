package erp.uniaura.modules.financeiro.repository;

import erp.uniaura.modules.financeiro.model.Mensalidade;
import erp.uniaura.modules.financeiro.model.StatusMensalidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MensalidadeRepository extends JpaRepository<Mensalidade, UUID> {

    Page<Mensalidade> findByAlunoId(UUID alunoId, Pageable pageable);

    Page<Mensalidade> findByStatus(StatusMensalidade status, Pageable pageable);

    Page<Mensalidade> findByAlunoIdAndStatus(UUID alunoId, StatusMensalidade status, Pageable pageable);

    boolean existsByAlunoIdAndCompetencia(UUID alunoId, String competencia);
}
