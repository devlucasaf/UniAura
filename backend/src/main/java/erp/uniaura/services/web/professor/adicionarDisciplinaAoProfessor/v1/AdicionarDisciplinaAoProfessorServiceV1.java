package erp.uniaura.services.web.professor.adicionarDisciplinaAoProfessor.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.ProfessorDisciplina;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.professor.VerificarProfessorHabilitadoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdicionarDisciplinaAoProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;
    private final BuscarDisciplinaComponent buscarDisciplinaComponent;
    private final VerificarProfessorHabilitadoComponent verificarProfessorHabilitadoComponent;

    // --- VINCULA A DISCIPLINA AO PROFESSOR, VALIDANDO DUPLICIDADE ---
    @Transactional
    public Uid adicionar(AdicionarDisciplinaAoProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getProfessorUid());
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());

        if (verificarProfessorHabilitadoComponent.habilitado(professor, disciplina.getId())) {
            throw new BusinessException("Esta disciplina já está vinculada ao professor.");
        }

        ProfessorDisciplina vinculo = new ProfessorDisciplina();

        vinculo.setProfessor(professor);
        vinculo.setDisciplinaId(disciplina.getId());
        vinculo.persist();

        return new Uid(vinculo.getUid());
    }
}
