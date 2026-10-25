package erp.uniaura.services.common.estagio;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAcessoAoEstagioComponent extends BaseComponent {

    // --- SÓ O ALUNO DONO DO ESTÁGIO OU A EQUIPE ACADÊMICA ACESSAM OS RELATÓRIOS ---
    public void validar(Estagio estagio, Usuario autenticado) {
        TipoUsuario perfil = autenticado.getRole();

        boolean equipe = perfil == TipoUsuario.ADMIN
                || perfil == TipoUsuario.COORDENADOR
                || perfil == TipoUsuario.SECRETARIA
                || perfil == TipoUsuario.PROFESSOR;

        if (equipe) {
            return;
        }

        if (!estagio.getAluno().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode acessar os relatórios do estágio de outro aluno.");
        }
    }
}
