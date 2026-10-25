package erp.uniaura.services.common.nota;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarPermissaoDeLancamentoDeNotaComponent extends BaseComponent {

    // --- SÓ O ADMIN, A COORDENAÇÃO E O PROFESSOR RESPONSÁVEL PELA DISCIPLINA DA TURMA MEXEM EM NOTAS ---
    public void validar(Usuario autenticado, TurmaDisciplina turmaDisciplina) {
        TipoUsuario perfil = autenticado.getRole();

        if (perfil == TipoUsuario.ADMIN || perfil == TipoUsuario.COORDENADOR) {
            return;
        }

        if (perfil == TipoUsuario.PROFESSOR) {
            if (turmaDisciplina.getProfessor().getUsuario().getId().equals(autenticado.getId())) {
                return;
            }

            throw new BusinessException("Apenas o professor responsável pela disciplina pode lançar notas para esta turma.");
        }

        throw new BusinessException("Você não tem permissão para lançar notas.");
    }
}
