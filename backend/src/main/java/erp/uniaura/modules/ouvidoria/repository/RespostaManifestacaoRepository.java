package erp.uniaura.modules.ouvidoria.repository;

import erp.uniaura.modules.ouvidoria.model.RespostaManifestacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaManifestacaoRepository extends JpaRepository<RespostaManifestacao, Long> {

    List<RespostaManifestacao> findByManifestacaoIdOrderByCriadoEmAsc(Long manifestacaoId);

    List<RespostaManifestacao> findByManifestacaoIdAndVisivelParaAutorTrueOrderByCriadoEmAsc(Long manifestacaoId);
}
