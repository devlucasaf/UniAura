package erp.uniaura.services.common.comunicado;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Comunicado;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAutorizacaoDoComunicadoComponent extends BaseComponent {

    // --- SÓ O AUTOR, A COORDENAÇÃO OU O ADMIN PODEM ALTERAR OU REMOVER O COMUNICADO ---
    public void validarPodeEditar(Comunicado comunicado, Usuario autenticado) {
        boolean equipeDeDirecao = autenticado.getRole() == TipoUsuario.ADMIN
                || autenticado.getRole() == TipoUsuario.COORDENADOR;

        if (equipeDeDirecao || comunicado.getAutor().getId().equals(autenticado.getId())) {
            return;
        }

        throw new BusinessException("Apenas o autor ou a coordenação podem alterar este comunicado.");
    }
}
