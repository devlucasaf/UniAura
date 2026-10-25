package erp.uniaura.services.web.frequencia.consultarResumoDeFrequenciaDoAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ConsultarResumoDeFrequenciaDoAlunoDisciplinaRetornoV1 {

    @Schema(description = "Identificador público da disciplina")
    private Long        disciplinaUid;
    private String      disciplinaNome;
    private Long        totalAulas;
    private Long        presencas;
    private Long        faltas;
    private BigDecimal  percentual;
}
