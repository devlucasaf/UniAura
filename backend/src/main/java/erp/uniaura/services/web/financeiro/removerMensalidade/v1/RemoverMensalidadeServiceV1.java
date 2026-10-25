package erp.uniaura.services.web.financeiro.removerMensalidade.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;
import erp.uniaura.services.common.financeiro.BuscarMensalidadeComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverMensalidadeServiceV1 extends BaseService {

    private final BuscarMensalidadeComponent buscarMensalidadeComponent;

    // --- MENSALIDADE JÁ PAGA TEM PAGAMENTO REGISTRADO E NÃO PODE SER APAGADA ---
    @Transactional
    public void remover(RemoverMensalidadeParamsV1 params) {
        Mensalidade mensalidade = buscarMensalidadeComponent.buscarPorUid(params.getUid());

        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Não é possível remover uma mensalidade já paga.");
        }

        mensalidade.remove();
    }
}
