package erp.uniaura.services.common.material;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Material;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAutorizacaoDoMaterialComponent extends BaseComponent {

    // --- SOMENTE PROFESSOR, COORDENAÇÃO E ADMIN PODE ALTERAR E REMOVER ---
    public void validarPodeEditar(Material material, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return;
        }

        if (!material.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pode editar este material.");
        }
    }
}
