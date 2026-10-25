package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.StatusMensalidade;

import org.springframework.stereotype.Component;

@Component
public class VerificarMensalidadeEmAbertoComponent extends BaseComponent {

    // --- SÓ SE COBRA OU SE QUITA MENSALIDADE PENDENTE OU ATRASADA ---
    public void validar(Mensalidade mensalidade) {
        if (mensalidade.getStatus() == StatusMensalidade.PAGA) {
            throw new BusinessException("Esta mensalidade já está paga.");
        }

        if (mensalidade.getStatus() == StatusMensalidade.CANCELADA) {
            throw new BusinessException("Não é possível cobrar ou pagar uma mensalidade cancelada.");
        }
    }
}
