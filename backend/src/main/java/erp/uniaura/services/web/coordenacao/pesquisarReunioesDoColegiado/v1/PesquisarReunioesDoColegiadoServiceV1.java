package erp.uniaura.services.web.coordenacao.pesquisarReunioesDoColegiado.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarReunioesDoColegiadoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- PESQUISA AS REUNIÕES DO COLEGIADO COM PAGINAÇÃO E FILTROS POR CURSO E STATUS ---
    @Transactional(readOnly = true)
    public PesquisarReunioesDoColegiadoRetornoV1 pesquisar(PesquisarReunioesDoColegiadoParamsV1 params) {
        Curso curso = null;

        if (params.getCursoUid() != null) {
            curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());
        }

        final Curso cursoFiltrado = curso;

        PageableQuery.Result<PesquisarReunioesDoColegiadoReuniaoRetornoV1> resultado =
                PageableQuery.<ReuniaoColegiado, PesquisarReunioesDoColegiadoReuniaoRetornoV1>builder()
                        .clazz(ReuniaoColegiado.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (cursoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("curso"), cursoFiltrado));
                            }

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarReunioesDoColegiadoRetornoV1 retorno = new PesquisarReunioesDoColegiadoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A REUNIÃO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarReunioesDoColegiadoReuniaoRetornoV1 paraRetorno(ReuniaoColegiado reuniao) {
        PesquisarReunioesDoColegiadoReuniaoRetornoV1 item = new PesquisarReunioesDoColegiadoReuniaoRetornoV1();

        item.setUid(reuniao.getUid());
        item.setCursoUid(reuniao.getCurso().getUid());
        item.setCursoNome(reuniao.getCurso().getNome());
        item.setTitulo(reuniao.getTitulo());
        item.setDataHora(reuniao.getDataHora());
        item.setLocal(reuniao.getLocal());
        item.setStatus(reuniao.getStatus());
        item.setEncerradaEm(reuniao.getEncerradaEm());

        if (reuniao.getCriadaPor() != null) {
            item.setCriadaPorNome(reuniao.getCriadaPor().getNome());
        }

        return item;
    }
}
