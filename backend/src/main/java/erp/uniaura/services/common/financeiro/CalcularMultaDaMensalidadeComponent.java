package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CalcularMultaDaMensalidadeComponent extends BaseComponent {

    private static final int        ESCALA                  = 2;
    private static final BigDecimal PERCENTUAL_MULTA_ATRASO = new BigDecimal("0.02");
    private static final BigDecimal PERCENTUAL_JUROS_AO_DIA = new BigDecimal("0.00033");

    // --- MULTA FIXA MAIS JUROS POR DIA DE ATRASO, SÓ PARA MENSALIDADE ATRASADA ---
    public BigDecimal calcular(Mensalidade mensalidade) {
        if (mensalidade.getStatus() != StatusMensalidade.ATRASADA) {
            return BigDecimal.ZERO.setScale(ESCALA, RoundingMode.HALF_UP);
        }

        long diasAtraso = Math.max(0, ChronoUnit.DAYS.between(mensalidade.getVencimento(), LocalDate.now()));
        BigDecimal percentualJuros = PERCENTUAL_JUROS_AO_DIA.multiply(BigDecimal.valueOf(diasAtraso));
        BigDecimal percentualTotal = PERCENTUAL_MULTA_ATRASO.add(percentualJuros);

        return mensalidade.getValor().multiply(percentualTotal).setScale(ESCALA, RoundingMode.HALF_UP);
    }

    // --- VALOR DA MENSALIDADE SOMADO À MULTA, QUE É O QUE O ALUNO PAGA HOJE ---
    public BigDecimal calcularValorTotal(Mensalidade mensalidade) {
        return mensalidade.getValor().add(calcular(mensalidade));
    }
}
