package erp.uniaura.modules.coordenacao.repository;

import erp.uniaura.modules.coordenacao.model.ParticipanteReuniao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipanteReuniaoRepository extends JpaRepository<ParticipanteReuniao, UUID> {

    List<ParticipanteReuniao> findByReuniaoIdOrderByCriadoEmAsc(UUID reuniaoId);

    Optional<ParticipanteReuniao> findByReuniaoIdAndUsuarioId(UUID reuniaoId, UUID usuarioId);

    boolean existsByReuniaoIdAndUsuarioId(UUID reuniaoId, UUID usuarioId);
}
