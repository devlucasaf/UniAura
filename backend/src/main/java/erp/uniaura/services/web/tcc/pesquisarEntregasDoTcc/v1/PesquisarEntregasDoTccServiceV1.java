package erp.uniaura.services.web.tcc.pesquisarEntregasDoTcc.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.EntregaTcc;
import erp.uniaura.persistence.entities.Tcc;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.tcc.BuscarTccComponent;
import erp.uniaura.services.common.tcc.VerificarAcessoAoTccComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarEntregasDoTccServiceV1 extends BaseService {

    private final BuscarTccComponent          buscarTccComponent;
    private final BuscarUsuarioComponent      buscarUsuarioComponent;
    private final VerificarAcessoAoTccComponent verificarAcessoAoTccComponent;

    @Transactional(readOnly = true)
    public PesquisarEntregasDoTccRetornoV1 pesquisar(Long usuarioId, PesquisarEntregasDoTccParamsV1 params) {
        Tcc tcc = buscarTccComponent.buscarPorUid(params.getTccUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoTccComponent.validar(tcc, autenticado);

        PageableQuery.Result<PesquisarEntregasDoTccEntregaRetornoV1> resultado =
                PageableQuery.<EntregaTcc, PesquisarEntregasDoTccEntregaRetornoV1>builder()
                        .clazz(EntregaTcc.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("tcc"), tcc)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarEntregasDoTccRetornoV1 retorno = new PesquisarEntregasDoTccRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    private PesquisarEntregasDoTccEntregaRetornoV1 paraRetorno(EntregaTcc entrega) {
        PesquisarEntregasDoTccEntregaRetornoV1 item = new PesquisarEntregasDoTccEntregaRetornoV1();

        item.setUid(entrega.getUid());
        item.setTipo(entrega.getTipo());
        item.setArquivoUrl(entrega.getArquivoUrl());
        item.setStatus(entrega.getStatus());
        item.setObservacoesOrientador(entrega.getObservacoesOrientador());
        item.setAnalisadoEm(entrega.getAnalisadoEm());
        item.setEnviadoEm(entrega.getEnviadoEm());

        return item;
    }
}
