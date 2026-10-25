package erp.uniaura.services.web.tcc.agendarDefesaDoTcc.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.StatusTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.tcc.BuscarTccComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendarDefesaDoTccServiceV1 extends BaseService {

    private final BuscarTccComponent buscarTccComponent;

    @Transactional
    public void agendar(AgendarDefesaDoTccParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getUid());

        if (tcc.getStatus() != StatusTcc.EM_ANDAMENTO) {
            throw new BusinessException("Só é possível agendar a defesa de um TCC em andamento.");
        }

        tcc.setDataDefesa(params.getDataDefesa());
        tcc.setStatus(StatusTcc.AGUARDANDO_BANCA);
        tcc.merge();
    }
}
