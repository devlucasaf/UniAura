package erp.uniaura.services.web.auditoria.pesquisarLogsDeAuditoria.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.LogAuditoria;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarLogsDeAuditoriaServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- A ENTIDADE TEM PRIORIDADE SOBRE O USUÁRIO; SEM NENHUM DOS DOIS, LISTA TUDO ---
    @Transactional(readOnly = true)
    public PesquisarLogsDeAuditoriaRetornoV1 pesquisar(PesquisarLogsDeAuditoriaParamsV1 params) {
        final boolean filtraPorEntidade = params.getEntidade() != null && !params.getEntidade().isBlank();

        Usuario usuario = null;

        if (!filtraPorEntidade && params.getUsuarioUid() != null) {
            usuario = buscarUsuarioComponent.buscarPorUid(params.getUsuarioUid());
        }

        final Usuario usuarioFiltrado = usuario;

        PageableQuery.Result<PesquisarLogsDeAuditoriaLogRetornoV1> resultado =
                PageableQuery.<LogAuditoria, PesquisarLogsDeAuditoriaLogRetornoV1>builder()
                        .clazz(LogAuditoria.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            if (filtraPorEntidade) {
                                criteria.where(cb().equal(cb().lower(from.get("entidade")), params.getEntidade().toLowerCase()));
                            } else if (usuarioFiltrado != null) {
                                criteria.where(cb().equal(from.get("usuario"), usuarioFiltrado));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarLogsDeAuditoriaRetornoV1 retorno = new PesquisarLogsDeAuditoriaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O LOG DE AUDITORIA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarLogsDeAuditoriaLogRetornoV1 paraRetorno(LogAuditoria log) {
        PesquisarLogsDeAuditoriaLogRetornoV1 item = new PesquisarLogsDeAuditoriaLogRetornoV1();

        item.setUid(log.getUid());
        item.setAcao(log.getAcao());
        item.setEntidade(log.getEntidade());
        item.setEntidadeId(log.getEntidadeId());
        item.setDetalhes(log.getDetalhes());
        item.setCriadoEm(log.getCriadoEm());

        if (log.getUsuario() != null) {
            item.setUsuarioUid(log.getUsuario().getUid());
            item.setUsuarioNome(log.getUsuario().getNome());
        }

        return item;
    }
}
