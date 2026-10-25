package erp.uniaura.services.web.turma.alterarTurma.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.curso.BuscarCursoComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;
    private final BuscarCursoComponent buscarCursoComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    @Transactional
    public void alterar(AlterarTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getUid());

        boolean codigoMudou = !turma.getCodigo().equalsIgnoreCase(params.getCodigo());
        if (codigoMudou && findByAttr(Turma.class, "codigo", params.getCodigo()).isPresent()) {
            throw new BusinessException("Já existe uma turma com o código: " + params.getCodigo());
        }

        if (!turma.getCurso().getUid().equals(params.getCursoUid())) {
            turma.setCurso(buscarCursoComponent.buscarPorUid(params.getCursoUid()));
        }

        if (params.getProfessorRegenteId() == null) {
            turma.setProfessorRegente(null);
        } else if (turma.getProfessorRegente() == null
                || !turma.getProfessorRegente().getId().equals(params.getProfessorRegenteId())) {
            turma.setProfessorRegente(buscarProfessorComponent.buscarPorId(params.getProfessorRegenteId()));
        }

        turma.setCodigo(params.getCodigo());
        turma.setPeriodoLetivo(params.getPeriodoLetivo());
        turma.setSerie(params.getSerie());
        turma.setSala(params.getSala());
        turma.setTurno(params.getTurno());
        turma.setCapacidadeMaxima(params.getCapacidadeMaxima());

        if (params.getAtiva() != null) {
            turma.setAtiva(params.getAtiva());
        }

        turma.merge();
    }
}
