package erp.uniaura.services.web.biblioteca.pagarMulta.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Multa;
import erp.uniaura.persistence.entities.StatusMulta;
import erp.uniaura.services.common.biblioteca.BuscarMultaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagarMultaServiceV1 extends BaseService {

    private final BuscarMultaComponent buscarMultaComponent;

    // --- MARCA A MULTA PENDENTE COMO PAGA ---
    @Transactional
    public void pagar(PagarMultaParamsV1 params) {
        Multa multa = buscarMultaComponent.buscarPorUid(params.getUid());

        if (multa.getStatus() != StatusMulta.PENDENTE) {
            throw new BusinessException("Somente multas PENDENTES podem ser pagas.");
        }

        multa.setStatus(StatusMulta.PAGA);
        multa.setPagaEm(LocalDateTime.now());
        multa.merge();
    }
}
