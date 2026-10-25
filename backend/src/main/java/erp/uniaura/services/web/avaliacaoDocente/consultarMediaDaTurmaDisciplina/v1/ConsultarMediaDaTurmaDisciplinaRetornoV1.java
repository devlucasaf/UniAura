package erp.uniaura.services.web.avaliacaoDocente.consultarMediaDaTurmaDisciplina.v1;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsultarMediaDaTurmaDisciplinaRetornoV1 {

    private Long        quantidadeAvaliacoes;
    private BigDecimal  mediaDidatica;
    private BigDecimal  mediaPontualidade;
    private BigDecimal  mediaDisponibilidade;
    private BigDecimal  mediaGeral;
}
