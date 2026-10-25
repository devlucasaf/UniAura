package erp.uniaura.services.web.turma.detalharTurma.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;

    @Transactional(readOnly = true)
    public DetalharTurmaRetornoV1 detalhar(DetalharTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getUid());
        Professor regente = turma.getProfessorRegente();

        DetalharTurmaRetornoV1 retorno = new DetalharTurmaRetornoV1();

        retorno.setUid(turma.getUid());
        retorno.setId(turma.getId());
        retorno.setCodigo(turma.getCodigo());
        retorno.setCursoUid(turma.getCurso().getUid());
        retorno.setCursoNome(turma.getCurso().getNome());
        retorno.setPeriodoLetivo(turma.getPeriodoLetivo());
        retorno.setSerie(turma.getSerie());
        retorno.setSala(turma.getSala());
        retorno.setTurno(turma.getTurno());
        retorno.setProfessorRegenteId(regente == null ? null : regente.getId());
        retorno.setProfessorRegenteNome(regente == null ? null : regente.getUsuario().getNome());
        retorno.setCapacidadeMaxima(turma.getCapacidadeMaxima());
        retorno.setAtiva(turma.getAtiva());
        retorno.setCriadoEm(turma.getCriadoEm());
        retorno.setAtualizadoEm(turma.getAtualizadoEm());

        return retorno;
    }
}
