package erp.uniaura.services.web.ouvidoria.detalharManifestacao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharManifestacaoParamsV1 {

    @Schema(description = "Identificador público da manifestação. Informe o uid ou o protocolo (um dos dois)")
    private Long uid;

    @Schema(description = "Número de protocolo da manifestação. Informe o uid ou o protocolo (um dos dois)")
    private String protocolo;
}
