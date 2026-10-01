package erp.uniaura.modules.relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RelatorioFinanceiroResponseDTO {

    private long        totalMensalidadesPendentes;
    private long        totalMensalidadesAtrasadas;
    private long        totalMensalidadesPagas;
    private long        totalMensalidadesCanceladas;
    private BigDecimal  valorEmAbertoPendente;
    private BigDecimal  valorEmAtraso;
    private BigDecimal  valorRecebido;
    private BigDecimal  taxaInadimplenciaPercentual;
}
