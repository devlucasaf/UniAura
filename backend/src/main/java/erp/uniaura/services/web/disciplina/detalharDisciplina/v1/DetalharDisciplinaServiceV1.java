package erp.uniaura.services.web.disciplina.detalharDisciplina.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharDisciplinaServiceV1 extends BaseService {

    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- DETALHA UMA DISCIPLINA PELO UID ---
    @Transactional(readOnly = true)
    public DetalharDisciplinaRetornoV1 detalhar(DetalharDisciplinaParamsV1 params) {
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getUid());

        DetalharDisciplinaRetornoV1 retorno = new DetalharDisciplinaRetornoV1();

        retorno.setUid(disciplina.getUid());
        retorno.setId(disciplina.getId());
        retorno.setCodigo(disciplina.getCodigo());
        retorno.setNome(disciplina.getNome());
        retorno.setEmenta(disciplina.getEmenta());
        retorno.setCargaHoraria(disciplina.getCargaHoraria());
        retorno.setCursoUid(disciplina.getCurso().getUid());
        retorno.setCursoNome(disciplina.getCurso().getNome());
        retorno.setPeriodo(disciplina.getPeriodo());
        retorno.setAtivo(disciplina.getAtivo());
        retorno.setCriadoEm(disciplina.getCriadoEm());
        retorno.setAtualizadoEm(disciplina.getAtualizadoEm());

        return retorno;
    }
}
