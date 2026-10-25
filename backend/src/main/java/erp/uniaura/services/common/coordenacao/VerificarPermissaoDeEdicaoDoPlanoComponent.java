package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TipoUsuario;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class VerificarPermissaoDeEdicaoDoPlanoComponent extends BaseComponent {

    // --- SÓ O PROFESSOR RESPONSÁVEL PELA TURMA E DISCIPLINA, A COORDENAÇÃO OU O ADMIN MEXEM NO PLANO ---
    public void validar(TurmaDisciplina turmaDisciplina, Usuario autenticado) {
        if (autenticado.getRole() == TipoUsuario.ADMIN || autenticado.getRole() == TipoUsuario.COORDENADOR) {
            return;
        }

        Professor professor = turmaDisciplina.getProfessor();

        if (professor.getUsuario() == null || !professor.getUsuario().getId().equals(autenticado.getId())) {
            throw new BusinessException("Apenas o professor responsável pela disciplina pode mexer neste plano de ensino.");
        }
    }
}
