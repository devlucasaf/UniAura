package erp.uniaura.services.web.ouvidoria.registrarManifestacao.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarManifestacaoRetornoV1 {

    @Schema(description = "Identificador público da manifestação")
    private Long uid;

    @Schema(description = "Número de protocolo para acompanhamento")
    private String protocolo;
}
