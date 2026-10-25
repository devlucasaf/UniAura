package erp.uniaura.services.web.biblioteca.gerarCodigoDeBarras.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GerarCodigoDeBarrasRetornoV1 {

    @Schema(description = "Código de barras sugerido, ainda não usado por nenhum exemplar")
    private String codigoBarras;
}
