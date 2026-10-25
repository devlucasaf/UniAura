package erp.uniaura.services.web.disciplina.pesquisarDisciplinasDoCurso.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarDisciplinasDoCursoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- PESQUISA AS DISCIPLINAS DE UM CURSO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarDisciplinasDoCursoRetornoV1 pesquisar(PesquisarDisciplinasDoCursoParamsV1 params) {
        Curso curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());

        PageableQuery.Result<PesquisarDisciplinasDoCursoDisciplinaRetornoV1> resultado =
                PageableQuery.<Disciplina, PesquisarDisciplinasDoCursoDisciplinaRetornoV1>builder()
                        .clazz(Disciplina.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("curso"), curso)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarDisciplinasDoCursoRetornoV1 retorno = new PesquisarDisciplinasDoCursoRetornoV1();
        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());
        return retorno;
    }

    // --- CONVERTE A DISCIPLINA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarDisciplinasDoCursoDisciplinaRetornoV1 paraRetorno(Disciplina disciplina) {
        PesquisarDisciplinasDoCursoDisciplinaRetornoV1 item = new PesquisarDisciplinasDoCursoDisciplinaRetornoV1();

        item.setUid(disciplina.getUid());
        item.setId(disciplina.getId());
        item.setCodigo(disciplina.getCodigo());
        item.setNome(disciplina.getNome());
        item.setEmenta(disciplina.getEmenta());
        item.setCargaHoraria(disciplina.getCargaHoraria());
        item.setPeriodo(disciplina.getPeriodo());
        item.setAtivo(disciplina.getAtivo());

        return item;
    }
}
