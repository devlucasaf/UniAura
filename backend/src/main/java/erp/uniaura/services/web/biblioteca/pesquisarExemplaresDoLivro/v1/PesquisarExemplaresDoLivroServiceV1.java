package erp.uniaura.services.web.biblioteca.pesquisarExemplaresDoLivro.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.Livro;
import erp.uniaura.services.common.biblioteca.BuscarLivroComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarExemplaresDoLivroServiceV1 extends BaseService {

    private final BuscarLivroComponent buscarLivroComponent;

    // --- PESQUISA OS EXEMPLARES DE UM LIVRO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarExemplaresDoLivroRetornoV1 pesquisar(PesquisarExemplaresDoLivroParamsV1 params) {
        Livro livro = buscarLivroComponent.buscarPorUid(params.getLivroUid());

        PageableQuery.Result<PesquisarExemplaresDoLivroExemplarRetornoV1> resultado =
                PageableQuery.<Exemplar, PesquisarExemplaresDoLivroExemplarRetornoV1>builder()
                        .clazz(Exemplar.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("livro"), livro)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarExemplaresDoLivroRetornoV1 retorno = new PesquisarExemplaresDoLivroRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O EXEMPLAR NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarExemplaresDoLivroExemplarRetornoV1 paraRetorno(Exemplar exemplar) {
        PesquisarExemplaresDoLivroExemplarRetornoV1 item = new PesquisarExemplaresDoLivroExemplarRetornoV1();

        item.setUid(exemplar.getUid());
        item.setCodigoBarras(exemplar.getCodigoBarras());
        item.setLocalizacao(exemplar.getLocalizacao());
        item.setStatus(exemplar.getStatus());
        item.setCriadoEm(exemplar.getCriadoEm());

        return item;
    }
}
