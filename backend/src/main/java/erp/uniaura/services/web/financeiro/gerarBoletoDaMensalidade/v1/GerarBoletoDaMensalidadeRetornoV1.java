package erp.uniaura.services.web.financeiro.gerarBoletoDaMensalidade.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GerarBoletoDaMensalidadeRetornoV1 {

    @Schema(description = "Linha digitável do boleto (fictícia)")
    private String     linhaDigitavel;

    @Schema(description = "Código de barras do boleto (fictício)")
    private String     codigoBarras;

    @Schema(description = "Valor a pagar, já com a multa se a mensalidade estiver atrasada")
    private BigDecimal valor;
    private LocalDate  vencimento;
}
