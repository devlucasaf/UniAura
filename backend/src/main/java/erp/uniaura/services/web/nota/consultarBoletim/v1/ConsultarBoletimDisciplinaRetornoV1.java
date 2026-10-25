package erp.uniaura.services.web.nota.consultarBoletim.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ConsultarBoletimDisciplinaRetornoV1 {

    @Schema(description = "Identificador público da disciplina")
    private Long                                disciplinaUid;

    private String                              disciplinaCodigo;
    private String                              disciplinaNome;

    @Schema(description = "Média ponderada pelo peso de cada nota")
    private BigDecimal                          mediaFinal;

    private List<ConsultarBoletimNotaRetornoV1> notas;
}
