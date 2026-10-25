package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Emprestimo;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CalcularDiasDeAtrasoComponent extends BaseComponent {

    // --- DIAS DE ATRASO ENTRE A DATA PREVISTA E A DA DEVOLUÇÃO, ARREDONDADOS PARA CIMA ---
    public int calcular(LocalDateTime previsto, LocalDateTime devolvido) {
        if (!devolvido.isAfter(previsto)) {
            return 0;
        }

        return (int) Math.ceil(Duration.between(previsto, devolvido).toHours() / 24.0);
    }

    // --- ATRASO DO EMPRÉSTIMO ATÉ A DEVOLUÇÃO ---
    public int calcularDoEmprestimo(Emprestimo emprestimo) {
        LocalDateTime base = LocalDateTime.now();

        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            base = emprestimo.getDataDevolucaoEfetiva();
        }

        return calcular(emprestimo.getDataDevolucaoPrevista(), base);
    }
}
