package erp.uniaura.modules.coordenacao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class MediaDisciplinaProjecao {

    private final UUID        alunoId;
    private final UUID        turmaDisciplinaId;
    private final BigDecimal  somaPonderada;
    private final BigDecimal  somaPesos;

    // --- MESMA FÓRMULA APLICADA PELO BOLETIM EM NotaService ---
    public BigDecimal mediaPonderada() {
        if (somaPesos == null || somaPesos.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return somaPonderada.divide(somaPesos, 2, RoundingMode.HALF_UP);
    }
}
