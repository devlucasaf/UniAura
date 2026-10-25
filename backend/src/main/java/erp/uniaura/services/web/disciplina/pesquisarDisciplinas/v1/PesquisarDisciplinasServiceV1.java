package erp.uniaura.services.web.disciplina.pesquisarDisciplinas.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarDisciplinasServiceV1 extends BaseService {

    // --- PESQUISA DISCIPLINAS COM PAGINAÇÃO E FILTRO POR CÓDIGO ---
    @Transactional(readOnly = true)
    public PesquisarDisciplinasRetornoV1 pesquisar(PesquisarDisciplinasParamsV1 params) {
        PageableQuery.Result<PesquisarDisciplinasDisciplinaRetornoV1> resultado =
                PageableQuery.<Disciplina, PesquisarDisciplinasDisciplinaRetornoV1>builder()
                        .clazz(Disciplina.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getCodigo() != null && !params.getCodigo().isBlank()) {
                                criteria.where(cb().equal(from.get("codigo"), params.getCodigo().trim()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarDisciplinasRetornoV1 retorno = new PesquisarDisciplinasRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A DISCIPLINA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarDisciplinasDisciplinaRetornoV1 paraRetorno(Disciplina disciplina) {
        PesquisarDisciplinasDisciplinaRetornoV1 item = new PesquisarDisciplinasDisciplinaRetornoV1();

        item.setUid(disciplina.getUid());
        item.setId(disciplina.getId());
        item.setCodigo(disciplina.getCodigo());
        item.setNome(disciplina.getNome());
        item.setCargaHoraria(disciplina.getCargaHoraria());
        item.setCursoUid(disciplina.getCurso().getUid());
        item.setCursoNome(disciplina.getCurso().getNome());
        item.setPeriodo(disciplina.getPeriodo());
        item.setAtivo(disciplina.getAtivo());

        return item;
    }
}
