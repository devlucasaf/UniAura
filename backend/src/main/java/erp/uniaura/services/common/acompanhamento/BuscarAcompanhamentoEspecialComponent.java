package erp.uniaura.services.common.acompanhamento;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;

import org.springframework.stereotype.Component;

@Component
public class BuscarAcompanhamentoEspecialComponent extends BaseComponent {

    // --- BUSCA ACOMPANHAMENTO ESPECIAL POR UID OU LANÇA EXCEÇÃO ---
    public AcompanhamentoEspecial buscarPorUid(Long uid) {
        return findByUid(AcompanhamentoEspecial.class, uid)
                .orElseThrow(() -> new BusinessException("Acompanhamento não encontrado."));
    }
}
