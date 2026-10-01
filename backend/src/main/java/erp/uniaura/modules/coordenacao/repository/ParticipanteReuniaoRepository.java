package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.ParticipanteReuniao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipanteReuniaoRepository extends JpaRepository<ParticipanteReuniao, Long> {

    List<ParticipanteReuniao> findByReuniaoIdOrderByCriadoEmAsc(Long reuniaoId);

    Optional<ParticipanteReuniao> findByReuniaoIdAndUsuarioId(Long reuniaoId, Long usuarioId);

    boolean existsByReuniaoIdAndUsuarioId(Long reuniaoId, Long usuarioId);
}
