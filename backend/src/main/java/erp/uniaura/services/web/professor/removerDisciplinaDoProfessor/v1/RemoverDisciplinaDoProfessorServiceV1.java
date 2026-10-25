package erp.uniaura.services.web.professor.removerDisciplinaDoProfessor.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.ProfessorDisciplina;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverDisciplinaDoProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;
    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- DESVINCULA A DISCIPLINA DO PROFESSOR ---
    @Transactional
    public void remover(RemoverDisciplinaDoProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        ProfessorDisciplina vinculo = em()
                .createQuery("select v from ProfessorDisciplina v where v.professor = :professor and v.disciplinaId = :disciplinaId", ProfessorDisciplina.class)
                .setParameter("professor", professor)
                .setParameter("disciplinaId", disciplina.getId())
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Esta disciplina não está vinculada ao professor."));

        vinculo.remove();
    }
}
