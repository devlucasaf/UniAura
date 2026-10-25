package erp.uniaura.services.web.calendario.removerEventoAcademico.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EventoAcademico;
import erp.uniaura.services.common.calendario.BuscarEventoAcademicoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverEventoAcademicoServiceV1 extends BaseService {

    private final BuscarEventoAcademicoComponent buscarEventoAcademicoComponent;

    // --- REMOVE O EVENTO DO CALENDÁRIO ACADÊMICO PELO UID ---
    @Transactional
    public void remover(RemoverEventoAcademicoParamsV1 params) {
        EventoAcademico evento = buscarEventoAcademicoComponent.buscarPorUid(params.getUid());
        evento.remove();
    }
}
