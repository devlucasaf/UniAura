package erp.uniaura.services.web.biblioteca.pesquisarMultas.v1;

import erp.uniaura.persistence.entities.StatusMulta;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarMultasMultaRetornoV1 {

    @Schema(description = "Identificador público da multa")
    private Long            uid;
    private String          usuarioNome;
    private String          livroTitulo;
    private BigDecimal      valor;
    private Integer         diasAtraso;
    private StatusMulta     status;
    private LocalDateTime   geradaEm;
    private LocalDateTime   pagaEm;
}
