package erp.uniaura.services.web.disciplina.alterarDisciplina.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.services.common.curso.BuscarCursoComponent;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarDisciplinaServiceV1 extends BaseService {

    private final BuscarDisciplinaComponent buscarDisciplinaComponent;
    private final BuscarCursoComponent buscarCursoComponent;

    // --- ALTERA A DISCIPLINA, VALIDANDO CÓDIGO ÚNICO E CURSO ---
    @Transactional
    public void alterar(AlterarDisciplinaParamsV1 params) {
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getUid());

        boolean codigoMudou = !disciplina.getCodigo().equalsIgnoreCase(params.getCodigo());
        if (codigoMudou && findByAttr(Disciplina.class, "codigo", params.getCodigo()).isPresent()) {
            throw new BusinessException("Já existe uma disciplina com o código: " + params.getCodigo());
        }

        if (!disciplina.getCurso().getUid().equals(params.getCursoUid())) {
            disciplina.setCurso(buscarCursoComponent.buscarPorUid(params.getCursoUid()));
        }

        disciplina.setCodigo(params.getCodigo());
        disciplina.setNome(params.getNome());
        disciplina.setEmenta(params.getEmenta());
        disciplina.setCargaHoraria(params.getCargaHoraria());
        disciplina.setPeriodo(params.getPeriodo());

        if (params.getAtivo() != null) {
            disciplina.setAtivo(params.getAtivo());
        }

        disciplina.merge();
    }
}
