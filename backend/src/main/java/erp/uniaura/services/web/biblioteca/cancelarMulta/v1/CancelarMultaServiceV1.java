package erp.uniaura.services.web.biblioteca.cancelarMulta.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Multa;
import erp.uniaura.persistence.entities.StatusMulta;
import erp.uniaura.services.common.biblioteca.BuscarMultaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelarMultaServiceV1 extends BaseService {

    private final BuscarMultaComponent buscarMultaComponent;

    // --- CANCELA A MULTA, DESDE QUE AINDA NÃO ESTEJA PAGA ---
    @Transactional
    public void cancelar(CancelarMultaParamsV1 params) {
        Multa multa = buscarMultaComponent.buscarPorUid(params.getUid());

        if (multa.getStatus() == StatusMulta.PAGA) {
            throw new BusinessException("Não é possível cancelar multa já paga.");
        }

        multa.setStatus(StatusMulta.CANCELADA);
        multa.merge();
    }
}
