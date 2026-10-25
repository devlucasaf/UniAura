package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Mensalidade;

import org.springframework.stereotype.Component;

@Component
public class BuscarMensalidadeComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DA MENSALIDADE ---
    public Mensalidade buscarPorUid(Long uid) {
        return findByUid(Mensalidade.class, uid)
                .orElseThrow(() -> new BusinessException("Mensalidade não encontrada."));
    }
}
