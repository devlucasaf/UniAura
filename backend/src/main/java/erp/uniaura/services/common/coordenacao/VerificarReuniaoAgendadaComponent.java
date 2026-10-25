package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ReuniaoColegiado;

import org.springframework.stereotype.Component;

@Component
public class VerificarReuniaoAgendadaComponent extends BaseComponent {

    // --- REUNIÃO JÁ REALIZADA OU CANCELADA NÃO ACEITA MAIS ALTERAÇÕES ---
    public void validar(ReuniaoColegiado reuniao, String acao) {
        if (reuniao.getStatus().isFinal()) {
            throw new BusinessException("A reunião está %s e não pode mais ser %s.".formatted(reuniao.getStatus(), acao));
        }
    }
}
