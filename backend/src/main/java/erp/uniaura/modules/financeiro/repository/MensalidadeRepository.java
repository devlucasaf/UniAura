package erp.uniaura.modules.financeiro.repository;

import erp.uniaura.modules.financeiro.model.Mensalidade;
import erp.uniaura.modules.financeiro.model.StatusMensalidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MensalidadeRepository extends JpaRepository<Mensalidade, Long> {

    Page<Mensalidade> findByAlunoId(Long alunoId, Pageable pageable);

    Page<Mensalidade> findByStatus(StatusMensalidade status, Pageable pageable);

    Page<Mensalidade> findByAlunoIdAndStatus(Long alunoId, StatusMensalidade status, Pageable pageable);

    boolean existsByAlunoIdAndCompetencia(Long alunoId, String competencia);

    List<Mensalidade> findByStatusAndVencimentoBefore(StatusMensalidade status, LocalDate data);

    long countByStatus(StatusMensalidade status);

    @Query("select coalesce(sum(m.valor), 0) from Mensalidade m where m.status = :status")
    BigDecimal somarValorPorStatus(StatusMensalidade status);
}
