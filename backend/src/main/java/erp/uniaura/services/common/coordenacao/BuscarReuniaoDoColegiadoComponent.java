package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ReuniaoColegiado;

import org.springframework.stereotype.Component;

@Component
public class BuscarReuniaoDoColegiadoComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DA REUNIÃO ---
    public ReuniaoColegiado buscarPorUid(Long uid) {
        return findByUid(ReuniaoColegiado.class, uid)
                .orElseThrow(() -> new BusinessException("Reunião do colegiado não encontrada."));
    }
}
