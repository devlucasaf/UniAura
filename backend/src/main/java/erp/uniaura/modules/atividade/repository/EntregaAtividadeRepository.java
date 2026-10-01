package erp.uniaura.modules.atividade.repository;

import erp.uniaura.modules.atividade.model.EntregaAtividade;
import erp.uniaura.modules.atividade.model.StatusEntrega;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaAtividadeRepository extends JpaRepository<EntregaAtividade, Long> {

    Optional<EntregaAtividade> findByAtividadeIdAndAlunoId(Long atividadeId, Long alunoId);

    boolean existsByAtividadeIdAndAlunoId(Long atividadeId, Long alunoId);

    List<EntregaAtividade> findByAtividadeId(Long atividadeId);

    Page<EntregaAtividade> findByAlunoId(Long alunoId, Pageable pageable);

    Page<EntregaAtividade> findByAlunoIdAndStatus(Long alunoId, StatusEntrega status, Pageable pageable);
}

