package erp.uniaura.modules.frequencia.dto;

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
public class FrequenciaPercentualDTO {

    private Long        alunoId;
    private String      alunoNome;
    private Long        disciplinaId;
    private String      disciplinaNome;
    private long        totalAulas;
    private long        presencas;
    private long        faltas;
    private BigDecimal  percentual;
}

