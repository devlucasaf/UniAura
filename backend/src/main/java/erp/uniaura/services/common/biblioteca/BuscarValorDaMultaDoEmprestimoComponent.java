package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Emprestimo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class BuscarValorDaMultaDoEmprestimoComponent extends BaseComponent {

    // --- VALOR DA MULTA GERADA PELO EMPRÉSTIMO, OU NULO QUANDO NÃO HOUVE ATRASO ---
    public BigDecimal buscar(Emprestimo emprestimo) {
        List<BigDecimal> valores = em()
                .createQuery("select m.valor from Multa m where m.emprestimo = :emprestimo", BigDecimal.class)
                .setParameter("emprestimo", emprestimo)
                .getResultList();

        if (valores.isEmpty()) {
            return null;
        }

        return valores.get(0);
    }
}
