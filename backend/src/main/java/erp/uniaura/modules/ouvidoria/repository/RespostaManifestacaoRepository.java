package erp.uniaura.modules.ouvidoria.repository;

import erp.uniaura.modules.ouvidoria.model.RespostaManifestacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RespostaManifestacaoRepository extends JpaRepository<RespostaManifestacao, UUID> {

    List<RespostaManifestacao> findByManifestacaoIdOrderByCriadoEmAsc(UUID manifestacaoId);

    List<RespostaManifestacao> findByManifestacaoIdAndVisivelParaAutorTrueOrderByCriadoEmAsc(UUID manifestacaoId);
}
