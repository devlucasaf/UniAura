package erp.uniaura.services.common.professor;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Professor;

import org.springframework.stereotype.Component;

@Component
public class VerificarProfessorHabilitadoComponent extends BaseComponent {

    // --- O PROFESSOR ESTÁ HABILITADO A LECIONAR A DISCIPLINA ---
    public boolean habilitado(Professor professor, Long disciplinaId) {
        Long vinculos = em()
                .createQuery("select count(pd) from ProfessorDisciplina pd where pd.professor = :professor and pd.disciplinaId = :disciplinaId", Long.class)
                .setParameter("professor", professor)
                .setParameter("disciplinaId", disciplinaId)
                .getSingleResult();

        return vinculos > 0;
    }
}
