package erp.uniaura.services.web.tcc.pesquisarMembrosDaBanca.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.BancaTccMembro;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.services.common.tcc.BuscarTccComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarMembrosDaBancaServiceV1 extends BaseService {

    private final BuscarTccComponent buscarTccComponent;

    @Transactional(readOnly = true)
    public PesquisarMembrosDaBancaRetornoV1 pesquisar(PesquisarMembrosDaBancaParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getTccUid());

        PageableQuery.Result<PesquisarMembrosDaBancaMembroRetornoV1> resultado =
                PageableQuery.<BancaTccMembro, PesquisarMembrosDaBancaMembroRetornoV1>builder()
                        .clazz(BancaTccMembro.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order("+id")
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("tcc"), tcc)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMembrosDaBancaRetornoV1 retorno = new PesquisarMembrosDaBancaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarMembrosDaBancaMembroRetornoV1 paraRetorno(BancaTccMembro membro) {
        PesquisarMembrosDaBancaMembroRetornoV1 item = new PesquisarMembrosDaBancaMembroRetornoV1();

        item.setUid(membro.getUid());
        item.setProfessorUid(membro.getProfessor().getUid());
        item.setProfessorNome(membro.getProfessor().getUsuario().getNome());
        item.setNota(membro.getNota());
        item.setParecer(membro.getParecer());

        return item;
    }
}
