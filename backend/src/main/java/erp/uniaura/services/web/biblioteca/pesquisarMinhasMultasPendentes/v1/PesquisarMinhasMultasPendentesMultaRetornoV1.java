package erp.uniaura.services.web.biblioteca.pesquisarMinhasMultasPendentes.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMinhasMultasPendentesMultaRetornoV1 {

    @Schema(description = "Identificador público da multa")
    private Long            uid;
    private String          livroTitulo;
    private BigDecimal      valor;
    private Integer         diasAtraso;
    private LocalDateTime   geradaEm;
}
