package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Multa;

import org.springframework.stereotype.Component;

@Component
public class BuscarMultaComponent extends BaseComponent {

    // --- BUSCA MULTA POR UID OU LANÇA EXCEÇÃO ---
    public Multa buscarPorUid(Long uid) {
        return findByUid(Multa.class, uid)
                .orElseThrow(() -> new BusinessException("Multa não encontrada."));
    }
}
