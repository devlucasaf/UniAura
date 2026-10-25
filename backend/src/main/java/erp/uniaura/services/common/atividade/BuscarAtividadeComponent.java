package erp.uniaura.services.common.atividade;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Atividade;

import org.springframework.stereotype.Component;

@Component
public class BuscarAtividadeComponent extends BaseComponent {

    // --- BUSCA ATIVIDADE POR UID OU LANÇA EXCEÇÃO ---
    public Atividade buscarPorUid(Long uid) {
        return findByUid(Atividade.class, uid)
                .orElseThrow(() -> new BusinessException("Atividade não encontrada."));
    }
}
