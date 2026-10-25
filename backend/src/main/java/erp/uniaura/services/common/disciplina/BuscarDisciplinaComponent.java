package erp.uniaura.services.common.disciplina;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Disciplina;

import org.springframework.stereotype.Component;

@Component
public class BuscarDisciplinaComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public Disciplina buscarPorUid(Long uid) {
        return findByUid(Disciplina.class, uid)
                .orElseThrow(() -> new BusinessException("Disciplina não encontrada."));
    }
}
