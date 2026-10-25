package erp.uniaura.services.common.tcc;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarAcessoAoTccComponent extends BaseComponent {

    // --- SÓ O ALUNO DONO DO TCC, OS PROFESSORES, A COORDENAÇÃO E O ADMIN ACESSAM AS ENTREGAS ---
    public void validar(Tcc tcc, Usuario autenticado) {
        TipoUsuario perfil = autenticado.getRole();

        boolean equipe = perfil == TipoUsuario.ADMIN
                || perfil == TipoUsuario.COORDENADOR
                || perfil == TipoUsuario.PROFESSOR;

        if (equipe) {
            return;
        }

        if (!tcc.getAluno().getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Você não pode acessar as entregas do TCC de outro aluno.");
        }
    }
}
