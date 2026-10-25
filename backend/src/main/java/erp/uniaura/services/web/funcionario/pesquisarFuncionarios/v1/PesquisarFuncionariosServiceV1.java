package erp.uniaura.services.web.funcionario.pesquisarFuncionarios.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Funcionario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PesquisarFuncionariosServiceV1 extends BaseService {

    // --- PESQUISA FUNCIONÁRIOS COM PAGINAÇÃO E FILTRO POR CARGO ---
    @Transactional(readOnly = true)
    public PesquisarFuncionariosRetornoV1 pesquisar(PesquisarFuncionariosParamsV1 params) {
        PageableQuery.Result<PesquisarFuncionariosFuncionarioRetornoV1> resultado =
                PageableQuery.<Funcionario, PesquisarFuncionariosFuncionarioRetornoV1>builder()
                        .clazz(Funcionario.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (params.getCargo() != null) {
                                criteria.where(cb().equal(from.get("cargo"), params.getCargo()));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarFuncionariosRetornoV1 retorno = new PesquisarFuncionariosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O FUNCIONÁRIO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarFuncionariosFuncionarioRetornoV1 paraRetorno(Funcionario funcionario) {
        PesquisarFuncionariosFuncionarioRetornoV1 item = new PesquisarFuncionariosFuncionarioRetornoV1();

        item.setUid(funcionario.getUid());
        item.setNome(funcionario.getUsuario().getNome());
        item.setEmail(funcionario.getUsuario().getEmail());
        item.setCargo(funcionario.getCargo());
        item.setDataAdmissao(funcionario.getDataAdmissao());
        item.setDepartamento(funcionario.getDepartamento());

        return item;
    }
}
