package erp.uniaura.services.common.comunicado;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Comunicado;

import org.springframework.stereotype.Component;

@Component
public class BuscarComunicadoComponent extends BaseComponent {

    // --- BUSCA COMUNICADO POR UID OU LANÇA EXCEÇÃO ---
    public Comunicado buscarPorUid(Long uid) {
        return findByUid(Comunicado.class, uid)
                .orElseThrow(() -> new BusinessException("Comunicado não encontrado."));
    }
}
