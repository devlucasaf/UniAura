package erp.uniaura.services.common.matricula;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Matricula;
import erp.uniaura.persistence.entities.StatusMatricula;

import org.springframework.stereotype.Component;

@Component
public class AlterarStatusDaMatriculaComponent extends BaseComponent {

    // --- SÓ MATRÍCULA ATIVA MUDA DE STATUS, E A TURMA ATUAL DO ALUNO É LIBERADA ---
    public void alterar(Matricula matricula, StatusMatricula novoStatus, String observacoes) {
        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new BusinessException("Só é possível alterar o status de uma matrícula que esteja ATIVA. Status atual: " + matricula.getStatus() + ".");
        }

        matricula.setStatus(novoStatus);

        if (observacoes != null) {
            matricula.setObservacoes(observacoes);
        }

        matricula.merge();

        Aluno aluno = matricula.getAluno();

        if (aluno.getTurmaAtualId() != null && aluno.getTurmaAtualId().equals(matricula.getTurma().getId())) {
            aluno.setTurmaAtualId(null);
            aluno.merge();
        }
    }
}
