package erp.uniaura.services.common.avaliacaoDocente;

import java.math.BigDecimal;

public record ResumoDeAvaliacaoDocente(
        long       quantidadeAvaliacoes,
        BigDecimal mediaDidatica,
        BigDecimal mediaPontualidade,
        BigDecimal mediaDisponibilidade,
        BigDecimal mediaGeral
) {
}
