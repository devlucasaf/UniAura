package erp.uniaura.services.common.professor;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarProfessorDaDisciplinaComponent extends BaseComponent {

    // --- SÓ O PROFESSOR RESPONSÁVEL PELA DISCIPLINA DA TURMA PODE OPERAR ---
    public Professor validar(TurmaDisciplina turmaDisciplina, Usuario autenticado) {
        Professor professor = turmaDisciplina.getProfessor();
        if (professor == null || professor.getUsuario() == null) {
            throw new BusinessException("Vínculo turma/disciplina não possui professor responsável.");
        }

        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return professor;
        }

        if (!professor.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pela disciplina pode realizar esta operação.");
        }

        return professor;
    }
}
