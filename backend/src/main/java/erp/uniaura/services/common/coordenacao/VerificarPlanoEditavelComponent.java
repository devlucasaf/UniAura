package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.PlanoEnsino;

import org.springframework.stereotype.Component;

@Component
public class VerificarPlanoEditavelComponent extends BaseComponent {

    // --- O CONTEÚDO SÓ MUDA ENQUANTO O PLANO ESTÁ EM RASCUNHO OU DEVOLVIDO ---
    public void validar(PlanoEnsino plano, String acao) {
        if (!plano.getStatus().isEditavel()) {
            throw new BusinessException("O plano está %s e só pode ser %s em RASCUNHO ou DEVOLVIDO.".formatted(plano.getStatus(), acao));
        }
    }
}
