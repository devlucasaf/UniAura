package erp.uniaura.services.common.matricula;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Matricula;

import org.springframework.stereotype.Component;

@Component
public class BuscarMatriculaComponent extends BaseComponent {

    // --- BUSCA MATRÍCULA POR UID OU LANÇA EXCEÇÃO ---
    public Matricula buscarPorUid(Long uid) {
        return findByUid(Matricula.class, uid)
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada."));
    }
}
