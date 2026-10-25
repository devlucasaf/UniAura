package erp.uniaura.services.web.processo.detalharProcesso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharProcessoParamsV1 {

    @Schema(description = "Identificador público do processo. Informe o uid ou o protocolo (um dos dois)")
    private Long uid;

    @Schema(description = "Número de protocolo do processo. Informe o uid ou o protocolo (um dos dois)")
    private String protocolo;
}
