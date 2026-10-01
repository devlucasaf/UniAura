package erp.uniaura.modules.coordenacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@AllArgsConstructor
public class MediaDisciplinaProjecao {

    private final Long        alunoId;
    private final Long        turmaDisciplinaId;
    private final BigDecimal  somaPonderada;
    private final BigDecimal  somaPesos;

    public BigDecimal mediaPonderada() {
        if (somaPesos == null || somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return somaPonderada.divide(somaPesos, 2, RoundingMode.HALF_UP);
    }
}
