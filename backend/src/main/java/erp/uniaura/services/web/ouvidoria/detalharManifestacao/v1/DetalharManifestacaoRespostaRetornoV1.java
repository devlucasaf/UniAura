package erp.uniaura.services.web.ouvidoria.detalharManifestacao.v1;

import erp.uniaura.persistence.entities.StatusManifestacao;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharManifestacaoRespostaRetornoV1 {

    @Schema(description = "Identificador público da resposta")
    private Long                uid;
    private String              autorNome;
    private StatusManifestacao  statusAnterior;
    private StatusManifestacao  statusNovo;
    private String              mensagem;

    @Schema(description = "Falso indica nota interna, que o autor da manifestação não enxerga")
    private Boolean             visivelParaAutor;
    private LocalDateTime       criadoEm;
}
