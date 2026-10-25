package erp.uniaura.services.common.atividade;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAutorizacaoDaAtividadeComponent extends BaseComponent {

    // --- SOMENTE PROFESSOR, COORDENAÇÃO OU ADMIN PODEM ALTERAR, REMOVER E AVALIAR ---
    public void validarPodeEditar(Atividade atividade, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return;
        }

        if (!atividade.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pode editar/avaliar esta atividade.");
        }
    }
}
