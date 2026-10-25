package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.Pagamento;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscarPagamentoDaMensalidadeComponent extends BaseComponent {

    // --- A MENSALIDADE TEM NO MÁXIMO UM PAGAMENTO, QUE SÓ EXISTE DEPOIS DE QUITADA ---
    public Optional<Pagamento> buscar(Mensalidade mensalidade) {
        List<Pagamento> encontrados = em()
                .createQuery("select p from Pagamento p where p.mensalidade = :mensalidade", Pagamento.class)
                .setParameter("mensalidade", mensalidade)
                .getResultList();

        if (encontrados.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(encontrados.get(0));
    }
}
