package erp.uniaura.services.web.turma.cadastrarTurma.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.curso.BuscarCursoComponent;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarTurmaServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;
    private final BuscarProfessorComponent buscarProfessorComponent;

    @Transactional
    public Uid cadastrar(CadastrarTurmaParamsV1 params) {
        if (findByAttr(Turma.class, "codigo", params.getCodigo()).isPresent()) {
            throw new BusinessException("Já existe uma turma com o código: " + params.getCodigo());
        }

        Curso curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());

        Professor professorRegente = null;
        if (params.getProfessorRegenteId() != null) {
            professorRegente = buscarProfessorComponent.buscarPorId(params.getProfessorRegenteId());
        }

        Turma turma = new Turma();

        turma.setCodigo(params.getCodigo());
        turma.setCurso(curso);
        turma.setPeriodoLetivo(params.getPeriodoLetivo());
        turma.setSerie(params.getSerie());
        turma.setSala(params.getSala());
        turma.setTurno(params.getTurno());
        turma.setProfessorRegente(professorRegente);
        turma.setCapacidadeMaxima(params.getCapacidadeMaxima());
        turma.setAtiva(params.getAtiva() == null ? Boolean.TRUE : params.getAtiva());
        turma.persist();

        return new Uid(turma.getUid());
    }
}
