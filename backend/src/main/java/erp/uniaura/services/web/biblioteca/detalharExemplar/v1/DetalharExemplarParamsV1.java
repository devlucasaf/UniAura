package erp.uniaura.services.web.biblioteca.detalharExemplar.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharExemplarParamsV1 {

    @Schema(description = "Identificador público do exemplar. Informe o uid ou o código de barras (um dos dois)")
    private Long uid;

    @Schema(description = "Código de barras do exemplar. Informe o uid ou o código de barras (um dos dois)")
    private String codigoBarras;
}
