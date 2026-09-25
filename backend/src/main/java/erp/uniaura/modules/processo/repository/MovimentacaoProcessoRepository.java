package erp.uniaura.modules.processo.repository;

import erp.uniaura.modules.processo.model.MovimentacaoProcesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovimentacaoProcessoRepository extends JpaRepository<MovimentacaoProcesso, UUID> {

    List<MovimentacaoProcesso> findByProcessoIdOrderByCriadoEmAsc(UUID processoId);

    List<MovimentacaoProcesso> findByProcessoIdAndVisivelParaAlunoTrueOrderByCriadoEmAsc(UUID processoId);
}
