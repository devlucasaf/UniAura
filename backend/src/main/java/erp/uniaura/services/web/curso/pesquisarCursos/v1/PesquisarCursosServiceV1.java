package erp.uniaura.services.web.curso.pesquisarCursos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarCursosServiceV1 extends BaseService {

    // --- PESQUISA CURSOS COM PAGINAÇÃO E FILTRO POR NÍVEL ---
    @Transactional(readOnly = true)
    public PesquisarCursosRetornoV1 pesquisar(PesquisarCursosParamsV1 params) {
        PageableQuery.Result<PesquisarCursosCursoRetornoV1> resultado = PageableQuery.<Curso, PesquisarCursosCursoRetornoV1>builder()
                .clazz(Curso.class)
                .entityManager(em())
                .first(params.getDe())
                .limit(params.getQtd())
                .order(params.getOrdem())
                .predicate((criteria, from) -> {
                    if (params.getNivel() != null) {
                        criteria.where(cb().equal(from.get("nivel"), params.getNivel()));
                    }
                })
                .converter(this::paraRetorno)
                .build()
                .query();

        PesquisarCursosRetornoV1 retorno = new PesquisarCursosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O CURSO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarCursosCursoRetornoV1 paraRetorno(Curso curso) {
        PesquisarCursosCursoRetornoV1 item = new PesquisarCursosCursoRetornoV1();

        item.setUid(curso.getUid());
        item.setId(curso.getId());
        item.setNome(curso.getNome());
        item.setNivel(curso.getNivel());
        item.setDuracaoSemestres(curso.getDuracaoSemestres());
        item.setCargaHorariaTotal(curso.getCargaHorariaTotal());
        item.setAtivo(curso.getAtivo());

        return item;
    }
}
