package erp.uniaura.modules.processo.repository;

import erp.uniaura.modules.processo.model.MovimentacaoProcesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoProcessoRepository extends JpaRepository<MovimentacaoProcesso, Long> {

    List<MovimentacaoProcesso> findByProcessoIdOrderByCriadoEmAsc(Long processoId);

    List<MovimentacaoProcesso> findByProcessoIdAndVisivelParaAlunoTrueOrderByCriadoEmAsc(Long processoId);
}
