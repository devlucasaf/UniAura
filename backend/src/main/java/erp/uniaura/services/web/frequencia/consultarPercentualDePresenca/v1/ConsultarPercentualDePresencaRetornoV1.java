package erp.uniaura.services.web.frequencia.consultarPercentualDePresenca.v1;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsultarPercentualDePresencaRetornoV1 {
    private String      alunoNome;
    private String      disciplinaNome;
    private Long        totalAulas;
    private Long        presencas;
    private Long        faltas;
    private BigDecimal  percentual;
}
