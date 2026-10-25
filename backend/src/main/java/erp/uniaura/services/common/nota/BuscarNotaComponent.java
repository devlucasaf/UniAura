package erp.uniaura.services.common.nota;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Nota;

import org.springframework.stereotype.Component;

@Component
public class BuscarNotaComponent extends BaseComponent {

    // --- BUSCA NOTA POR UID OU LANÇA EXCEÇÃO ---
    public Nota buscarPorUid(Long uid) {
        return findByUid(Nota.class, uid)
                .orElseThrow(() -> new BusinessException("Nota não encontrada."));
    }
}
