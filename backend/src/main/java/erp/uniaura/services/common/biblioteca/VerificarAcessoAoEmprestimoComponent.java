package erp.uniaura.services.common.biblioteca;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Emprestimo;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAcessoAoEmprestimoComponent extends BaseComponent {

    // --- SÓ O DONO DO EMPRÉSTIMO OU A EQUIPE DA BIBLIOTECA ACESSAM O EMPRÉSTIMO ---
    public void validar(Emprestimo emprestimo, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.BIBLIOTECARIO) {
            return;
        }

        if (autenticado.getRole() == TipoUsuario.ADMIN) {
            return;
        }

        if (!emprestimo.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode acessar o empréstimo de outro usuário.");
        }
    }
}
