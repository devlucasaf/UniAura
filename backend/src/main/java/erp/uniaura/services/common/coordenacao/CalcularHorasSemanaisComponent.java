package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;

@Component
public class CalcularHorasSemanaisComponent extends BaseComponent {

    private static final int        ESCALA              = 2;
    private static final BigDecimal MINUTOS_POR_HORA    = new BigDecimal("60");

    // --- DURAÇÃO SEMANAL DE UMA AULA, EM HORAS ---
    public BigDecimal horasDaAula(TurmaDisciplina aula) {
        long minutos = Duration.between(aula.getHorarioInicio(), aula.getHorarioFim()).toMinutes();

        return BigDecimal.valueOf(minutos).divide(MINUTOS_POR_HORA, ESCALA, RoundingMode.HALF_UP);
    }

    // --- SOMA DA DURAÇÃO SEMANAL DAS AULAS INFORMADAS ---
    public BigDecimal somar(List<TurmaDisciplina> aulas) {
        BigDecimal total = BigDecimal.ZERO;

        for (TurmaDisciplina aula : aulas) {
            total = total.add(horasDaAula(aula));
        }

        return total.setScale(ESCALA, RoundingMode.HALF_UP);
    }
}
