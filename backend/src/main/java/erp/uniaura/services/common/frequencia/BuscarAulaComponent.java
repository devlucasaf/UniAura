package erp.uniaura.services.common.frequencia;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aula;

import org.springframework.stereotype.Component;

@Component
public class BuscarAulaComponent extends BaseComponent {

    // --- BUSCA AULA POR UID OU LANÇA EXCEÇÃO ---
    public Aula buscarPorUid(Long uid) {
        return findByUid(Aula.class, uid)
                .orElseThrow(() -> new BusinessException("Aula não encontrada."));
    }
}
