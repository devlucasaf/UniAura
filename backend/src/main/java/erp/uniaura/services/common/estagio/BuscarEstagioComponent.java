package erp.uniaura.services.common.estagio;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Estagio;

import org.springframework.stereotype.Component;

@Component
public class BuscarEstagioComponent extends BaseComponent {

    // --- BUSCA ESTÁGIO POR UID OU LANÇA EXCEÇÃO ---
    public Estagio buscarPorUid(Long uid) {
        return findByUid(Estagio.class, uid)
                .orElseThrow(() -> new BusinessException("Estágio não encontrado."));
    }
}
