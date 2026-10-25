package erp.uniaura.services.common.turma;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Turma;

import org.springframework.stereotype.Component;

@Component
public class BuscarTurmaComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public Turma buscarPorUid(Long uid) {
        return findByUid(Turma.class, uid)
                .orElseThrow(() -> new BusinessException("Turma não encontrada."));
    }

    // --- USO TRANSITÓRIO DOS SERVICES PELO ID SEQUENCIAL ---
    public Turma buscarPorId(Long id) {
        return findById(Turma.class, id)
                .orElseThrow(() -> new BusinessException("Turma não encontrada."));
    }
}
