package erp.uniaura.services.web.biblioteca.pesquisarMultas.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Multa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarMultasServiceV1 extends BaseService {

    // --- PESQUISA AS MULTAS FILTRADAS POR STATUS COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMultasRetornoV1 pesquisar(PesquisarMultasParamsV1 params) {
        PageableQuery.Result<PesquisarMultasMultaRetornoV1> resultado =
                PageableQuery.<Multa, PesquisarMultasMultaRetornoV1>builder()
                        .clazz(Multa.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("status"), params.getStatus())))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMultasRetornoV1 retorno = new PesquisarMultasRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MULTA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMultasMultaRetornoV1 paraRetorno(Multa multa) {
        PesquisarMultasMultaRetornoV1 item = new PesquisarMultasMultaRetornoV1();

        item.setUid(multa.getUid());
        item.setUsuarioNome(multa.getEmprestimo().getUsuario().getNome());
        item.setLivroTitulo(multa.getEmprestimo().getExemplar().getLivro().getTitulo());
        item.setValor(multa.getValor());
        item.setDiasAtraso(multa.getDiasAtraso());
        item.setStatus(multa.getStatus());
        item.setGeradaEm(multa.getGeradaEm());
        item.setPagaEm(multa.getPagaEm());

        return item;
    }
}
