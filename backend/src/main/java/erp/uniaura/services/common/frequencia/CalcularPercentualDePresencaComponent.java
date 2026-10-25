package erp.uniaura.services.common.frequencia;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Frequencia;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class CalcularPercentualDePresencaComponent extends BaseComponent {

    // --- PRESENÇAS SOBRE O TOTAL DE AULAS, EM PERCENTUAL COM DUAS CASAS ---
    public PercentualDePresenca calcular(List<Frequencia> frequencias) {
        long total = frequencias.size();
        long presentes = frequencias.stream().filter(frequencia -> Boolean.TRUE.equals(frequencia.getPresente())).count();
        long faltas = total - presentes;

        BigDecimal percentual = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        if (total > 0) {
            percentual = BigDecimal.valueOf(presentes)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);
        }

        return new PercentualDePresenca(total, presentes, faltas, percentual);
    }
}
