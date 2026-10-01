package erp.uniaura.modules.biblioteca.multa.repository;

import erp.uniaura.modules.biblioteca.multa.model.Multa;
import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MultaRepository extends JpaRepository<Multa, Long> {

    Page<Multa> findByStatus(StatusMulta status, Pageable pageable);

    List<Multa> findByEmprestimoUsuarioIdAndStatus(Long usuarioId, StatusMulta status);

    long countByStatus(StatusMulta status);

    @Query("select coalesce(sum(m.valor), 0) from Multa m where m.status = :status")
    BigDecimal somarValorPorStatus(StatusMulta status);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
              FROM Multa m
             WHERE m.emprestimo.usuario.id = :usuarioId
               AND m.status = erp.uniaura.modules.biblioteca.multa.model.StatusMulta.PENDENTE
            """)
    boolean existePendenteDoUsuario(@Param("usuarioId") Long usuarioId);
}

