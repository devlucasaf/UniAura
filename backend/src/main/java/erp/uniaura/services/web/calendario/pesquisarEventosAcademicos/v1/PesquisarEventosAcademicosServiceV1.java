package erp.uniaura.services.web.calendario.pesquisarEventosAcademicos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EventoAcademico;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarEventosAcademicosServiceV1 extends BaseService {

    // --- PESQUISA EVENTOS ACADÊMICOS COM PAGINAÇÃO E FILTRO POR TIPO ---
    @Transactional(readOnly = true)
    public PesquisarEventosAcademicosRetornoV1 pesquisar(PesquisarEventosAcademicosParamsV1 params) {
        PageableQuery.Result<PesquisarEventosAcademicosEventoRetornoV1> resultado =
                PageableQuery.<EventoAcademico, PesquisarEventosAcademicosEventoRetornoV1>builder()
                        .clazz(EventoAcademico.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getTipo() != null) {
                                criteria.where(cb().equal(from.get("tipo"), params.getTipo()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarEventosAcademicosRetornoV1 retorno = new PesquisarEventosAcademicosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O EVENTO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarEventosAcademicosEventoRetornoV1 paraRetorno(EventoAcademico evento) {
        PesquisarEventosAcademicosEventoRetornoV1 item = new PesquisarEventosAcademicosEventoRetornoV1();

        item.setUid(evento.getUid());
        item.setTitulo(evento.getTitulo());
        item.setDescricao(evento.getDescricao());
        item.setDataInicio(evento.getDataInicio());
        item.setDataFim(evento.getDataFim());
        item.setTipo(evento.getTipo());
        item.setPublico(evento.getPublico());

        return item;
    }
}
