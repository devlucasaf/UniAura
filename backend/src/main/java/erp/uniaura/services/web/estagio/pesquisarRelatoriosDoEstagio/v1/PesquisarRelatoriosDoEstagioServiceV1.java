package erp.uniaura.services.web.estagio.pesquisarRelatoriosDoEstagio.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Estagio;
import erp.uniaura.persistence.entities.RelatorioEstagio;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.estagio.BuscarEstagioComponent;
import erp.uniaura.services.common.estagio.VerificarAcessoAoEstagioComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarRelatoriosDoEstagioServiceV1 extends BaseService {

    private final BuscarEstagioComponent buscarEstagioComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAcessoAoEstagioComponent verificarAcessoAoEstagioComponent;

    // --- PESQUISA OS RELATÓRIOS DO ESTÁGIO COM PAGINAÇÃO APÓS VALIDAR O ACESSO ---
    @Transactional(readOnly = true)
    public PesquisarRelatoriosDoEstagioRetornoV1 pesquisar(Long usuarioId, PesquisarRelatoriosDoEstagioParamsV1 params) {
        Estagio estagio = buscarEstagioComponent.buscarPorUid(params.getEstagioUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAcessoAoEstagioComponent.validar(estagio, autenticado);

        PageableQuery.Result<PesquisarRelatoriosDoEstagioRelatorioRetornoV1> resultado =
                PageableQuery.<RelatorioEstagio, PesquisarRelatoriosDoEstagioRelatorioRetornoV1>builder()
                        .clazz(RelatorioEstagio.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("estagio"), estagio)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarRelatoriosDoEstagioRetornoV1 retorno = new PesquisarRelatoriosDoEstagioRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O RELATÓRIO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarRelatoriosDoEstagioRelatorioRetornoV1 paraRetorno(RelatorioEstagio relatorio) {
        PesquisarRelatoriosDoEstagioRelatorioRetornoV1 item = new PesquisarRelatoriosDoEstagioRelatorioRetornoV1();

        item.setUid(relatorio.getUid());
        item.setPeriodoReferencia(relatorio.getPeriodoReferencia());
        item.setHorasRegistradas(relatorio.getHorasRegistradas());
        item.setDescricaoAtividades(relatorio.getDescricaoAtividades());
        item.setComprovanteUrl(relatorio.getComprovanteUrl());
        item.setStatus(relatorio.getStatus());

        if (relatorio.getAnalisadoPor() != null) {
            item.setAnalisadoPorNome(relatorio.getAnalisadoPor().getNome());
        }

        item.setObservacoes(relatorio.getObservacoes());
        item.setAnalisadoEm(relatorio.getAnalisadoEm());
        item.setEnviadoEm(relatorio.getEnviadoEm());

        return item;
    }
}
