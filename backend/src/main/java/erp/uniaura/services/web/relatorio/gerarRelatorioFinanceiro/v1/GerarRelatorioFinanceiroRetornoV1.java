package erp.uniaura.services.web.relatorio.gerarRelatorioFinanceiro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GerarRelatorioFinanceiroRetornoV1 {

    private Long        totalMensalidadesPendentes;
    private Long        totalMensalidadesAtrasadas;
    private Long        totalMensalidadesPagas;
    private Long        totalMensalidadesCanceladas;
    private BigDecimal  valorEmAbertoPendente;
    private BigDecimal  valorEmAtraso;
    private BigDecimal  valorRecebido;

    @Schema(description = "Mensalidades atrasadas sobre o total de pendentes, atrasadas e pagas, em percentual")
    private BigDecimal  taxaInadimplenciaPercentual;
}
