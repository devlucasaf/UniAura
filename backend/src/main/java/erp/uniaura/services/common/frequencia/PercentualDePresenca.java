package erp.uniaura.services.common.frequencia;

import java.math.BigDecimal;

public record PercentualDePresenca(
        long       totalAulas,
        long       presencas,
        long       faltas,
        BigDecimal percentual
) {
}
