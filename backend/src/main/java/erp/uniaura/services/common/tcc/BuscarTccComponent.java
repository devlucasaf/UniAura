package erp.uniaura.services.common.tcc;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Tcc;

import org.springframework.stereotype.Component;

@Component
public class BuscarTccComponent extends BaseComponent {

    // --- BUSCA TCC POR UID OU LANÇA EXCEÇÃO ---
    public Tcc buscarPorUid(Long uid) {
        return findByUid(Tcc.class, uid)
                .orElseThrow(() -> new BusinessException("TCC não encontrado."));
    }
}
