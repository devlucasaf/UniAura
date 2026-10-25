package erp.uniaura.services.web.nota.consultarBoletim.v1;

import erp.uniaura.persistence.entities.PeriodoAvaliacao;
import erp.uniaura.persistence.entities.TipoAvaliacao;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsultarBoletimNotaRetornoV1 {

    private PeriodoAvaliacao    periodoAvaliacao;
    private TipoAvaliacao       tipoAvaliacao;
    private BigDecimal          valor;
    private BigDecimal          peso;
}
