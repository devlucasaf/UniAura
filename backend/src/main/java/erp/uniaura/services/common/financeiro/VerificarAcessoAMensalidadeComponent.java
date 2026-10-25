package erp.uniaura.services.common.financeiro;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAcessoAMensalidadeComponent extends BaseComponent {

    // --- A EQUIPE FINANCEIRA VÊ QUALQUER MENSALIDADE ---
    public void validar(Mensalidade mensalidade, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN) {
            return;
        }

        if (autenticado.getRole() == TipoUsuario.FINANCEIRO) {
            return;
        }

        if (autenticado.getRole() == TipoUsuario.SECRETARIA) {
            return;
        }

        if (mensalidade.getAluno().getUsuario() == null) {
            throw new BusinessException("Você não pode acessar a mensalidade de outro aluno.");
        }

        if (!mensalidade.getAluno().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode acessar a mensalidade de outro aluno.");
        }
    }
}
