package erp.uniaura.services.web.responsavel.pesquisarResponsaveis.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Responsavel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarResponsaveisServiceV1 extends BaseService {

    // --- PESQUISA RESPONSÁVEIS COM PAGINAÇÃO E FILTRO POR PARENTESCO ---
    @Transactional(readOnly = true)
    public PesquisarResponsaveisRetornoV1 pesquisar(PesquisarResponsaveisParamsV1 params) {
        PageableQuery.Result<PesquisarResponsaveisResponsavelRetornoV1> resultado =
                PageableQuery.<Responsavel, PesquisarResponsaveisResponsavelRetornoV1>builder()
                        .clazz(Responsavel.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getParentesco() != null) {
                                criteria.where(cb().equal(from.get("parentesco"), params.getParentesco()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarResponsaveisRetornoV1 retorno = new PesquisarResponsaveisRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O RESPONSÁVEL NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarResponsaveisResponsavelRetornoV1 paraRetorno(Responsavel responsavel) {
        PesquisarResponsaveisResponsavelRetornoV1 item = new PesquisarResponsaveisResponsavelRetornoV1();

        item.setUid(responsavel.getUid());
        item.setNome(responsavel.getUsuario().getNome());
        item.setEmail(responsavel.getUsuario().getEmail());
        item.setTelefone(responsavel.getUsuario().getTelefone());
        item.setParentesco(responsavel.getParentesco());

        return item;
    }
}
