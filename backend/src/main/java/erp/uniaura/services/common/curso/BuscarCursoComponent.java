package erp.uniaura.services.common.curso;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Curso;

import org.springframework.stereotype.Component;

@Component
public class BuscarCursoComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DAS FUNCIONALIDADES NOVAS ---
    public Curso buscarPorUid(Long uid) {
        return findByUid(Curso.class, uid)
                .orElseThrow(() -> new BusinessException("Curso não encontrado."));
    }

    // --- PELO ID SEQUENCIAL ---
    public Curso buscarPorId(Long id) {
        return findById(Curso.class, id)
                .orElseThrow(() -> new BusinessException("Curso não encontrado."));
    }
}
