package erp.uniaura.services.web.disciplina.cadastrarDisciplina.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarDisciplinaServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- CADASTRA UMA DISCIPLINA, VALIDANDO CÓDIGO ÚNICO E CURSO ---
    @Transactional
    public Uid cadastrar(CadastrarDisciplinaParamsV1 params) {
        if (findByAttr(Disciplina.class, "codigo", params.getCodigo()).isPresent()) {
            throw new BusinessException("Já existe uma disciplina com o código: " + params.getCodigo());
        }

        Curso curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());

        Disciplina disciplina = new Disciplina();

        disciplina.setCodigo(params.getCodigo());
        disciplina.setNome(params.getNome());
        disciplina.setEmenta(params.getEmenta());
        disciplina.setCargaHoraria(params.getCargaHoraria());
        disciplina.setCurso(curso);
        disciplina.setPeriodo(params.getPeriodo());
        disciplina.setAtivo(params.getAtivo() == null ? Boolean.TRUE : params.getAtivo());
        disciplina.persist();

        return new Uid(disciplina.getUid());
    }
}
