package erp.uniaura.services.web.processo.abrirProcesso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbrirProcessoRetornoV1 {

    @Schema(description = "Identificador público do processo")
    private Long uid;

    @Schema(description = "Número de protocolo para acompanhamento")
    private String protocolo;
}
