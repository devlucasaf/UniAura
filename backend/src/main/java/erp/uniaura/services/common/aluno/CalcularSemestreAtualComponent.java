package erp.uniaura.services.common.aluno;

import cloudsupport.services.BaseComponent;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CalcularSemestreAtualComponent extends BaseComponent {

    // --- SEMESTRE EM CURSO CONTADO DESDE O INGRESSO; SEM DATA DE INGRESSO, É O PRIMEIRO ---
    public int calcular(LocalDate ingresso) {
        if (ingresso == null) {
            return 1;
        }

        LocalDate hoje = LocalDate.now();

        int semestreHoje = 2;

        if (hoje.getMonthValue() <= 6) {
            semestreHoje = 1;
        }

        int semestreIngresso = 2;

        if (ingresso.getMonthValue() <= 6) {
            semestreIngresso = 1;
        }

        return Math.max(1, (hoje.getYear() - ingresso.getYear()) * 2 + (semestreHoje - semestreIngresso) + 1);
    }
}