package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Reserva;

import org.springframework.stereotype.Component;

@Component
public class BuscarReservaComponent extends BaseComponent {

    // --- BUSCA RESERVA POR UID OU LANÇA EXCEÇÃO ---
    public Reserva buscarPorUid(Long uid) {
        return findByUid(Reserva.class, uid)
                .orElseThrow(() -> new BusinessException("Reserva não encontrada."));
    }
}
