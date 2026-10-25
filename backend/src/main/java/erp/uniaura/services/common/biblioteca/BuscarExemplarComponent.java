package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Exemplar;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarExemplarComponent extends BaseComponent {

    // --- BUSCA EXEMPLAR POR UID OU LANÇA EXCEÇÃO ---
    public Exemplar buscarPorUid(Long uid) {
        return findByUid(Exemplar.class, uid)
                .orElseThrow(() -> new BusinessException("Exemplar não encontrado."));
    }

    // --- BUSCA EXEMPLAR PELO CÓDIGO DE BARRAS OU LANÇA EXCEÇÃO ---
    public Exemplar buscarPorCodigoDeBarras(String codigoBarras) {
        List<Exemplar> encontrados = em()
                .createQuery("select e from Exemplar e where e.codigoBarras = :codigoBarras", Exemplar.class)
                .setParameter("codigoBarras", codigoBarras.trim())
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("Exemplar não encontrado.");
        }

        return encontrados.get(0);
    }
}
