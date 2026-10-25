package erp.uniaura.services.web.coordenacao.definirProfessorRegenteDaTurma.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefinirProfessorRegenteDaTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- SEM PROFESSOR INFORMADO, A TURMA FICA SEM REGENTE ---
    @Transactional
    public void definir(DefinirProfessorRegenteDaTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getTurmaUid());

        if (params.getProfessorUid() == null) {
            turma.setProfessorRegente(null);
        } else {
            turma.setProfessorRegente(buscarProfessorComponent.buscarPorUid(params.getProfessorUid()));
        }

        turma.merge();
    }
}
