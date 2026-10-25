package erp.uniaura.services.web.ouvidoria.pesquisarMinhasManifestacoes.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Manifestacao;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarMinhasManifestacoesServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- PESQUISA AS MANIFESTAÇÕES DO PRÓPRIO USUÁRIO COM PAGINAÇÃO E FILTRO POR STATUS ---
    @Transactional(readOnly = true)
    public PesquisarMinhasManifestacoesRetornoV1 pesquisar(Long usuarioId, PesquisarMinhasManifestacoesParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        PageableQuery.Result<PesquisarMinhasManifestacoesManifestacaoRetornoV1> resultado =
                PageableQuery.<Manifestacao, PesquisarMinhasManifestacoesManifestacaoRetornoV1>builder()
                        .clazz(Manifestacao.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("autor"), autenticado));

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMinhasManifestacoesRetornoV1 retorno = new PesquisarMinhasManifestacoesRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MANIFESTAÇÃO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMinhasManifestacoesManifestacaoRetornoV1 paraRetorno(Manifestacao manifestacao) {
        PesquisarMinhasManifestacoesManifestacaoRetornoV1 item = new PesquisarMinhasManifestacoesManifestacaoRetornoV1();

        item.setUid(manifestacao.getUid());
        item.setProtocolo(manifestacao.getProtocolo());
        item.setTipo(manifestacao.getTipo());
        item.setAssunto(manifestacao.getAssunto());
        item.setSetor(manifestacao.getSetor());
        item.setStatus(manifestacao.getStatus());
        item.setAnonima(manifestacao.getAnonima());
        item.setPrazoResposta(manifestacao.getPrazoResposta());
        item.setCriadoEm(manifestacao.getCriadoEm());

        return item;
    }
}
