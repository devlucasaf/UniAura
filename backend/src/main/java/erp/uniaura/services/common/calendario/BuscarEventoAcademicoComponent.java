package erp.uniaura.services.common.calendario;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.EventoAcademico;

import org.springframework.stereotype.Component;

@Component
public class BuscarEventoAcademicoComponent extends BaseComponent {

    // --- BUSCA O EVENTO POR UID ---
    public EventoAcademico buscarPorUid(Long uid) {
        return findByUid(EventoAcademico.class, uid)
                .orElseThrow(() -> new BusinessException("Evento acadêmico não encontrado."));
    }
}
