package erp.uniaura.services.web.ouvidoria.pesquisarManifestacoes.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Manifestacao;

import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PesquisarManifestacoesServiceV1 extends BaseService {

    // --- PESQUISA MANIFESTAÇÕES COM PAGINAÇÃO E FILTROS POR STATUS E TIPO ---
    @Transactional(readOnly = true)
    public PesquisarManifestacoesRetornoV1 pesquisar(PesquisarManifestacoesParamsV1 params) {
        PageableQuery.Result<PesquisarManifestacoesManifestacaoRetornoV1> resultado =
                PageableQuery.<Manifestacao, PesquisarManifestacoesManifestacaoRetornoV1>builder()
                        .clazz(Manifestacao.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            if (params.getTipo() != null) {
                                condicoes.add(cb().equal(from.get("tipo"), params.getTipo()));
                            }

                            if (!condicoes.isEmpty()) {
                                criteria.where(condicoes.toArray(new Predicate[0]));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarManifestacoesRetornoV1 retorno = new PesquisarManifestacoesRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MANIFESTAÇÃO NO ITEM DE RETORNO, RESPEITANDO O SIGILO DO AUTOR ---
    private PesquisarManifestacoesManifestacaoRetornoV1 paraRetorno(Manifestacao manifestacao) {
        PesquisarManifestacoesManifestacaoRetornoV1 item = new PesquisarManifestacoesManifestacaoRetornoV1();

        item.setUid(manifestacao.getUid());
        item.setProtocolo(manifestacao.getProtocolo());
        item.setTipo(manifestacao.getTipo());
        item.setAssunto(manifestacao.getAssunto());
        item.setSetor(manifestacao.getSetor());
        item.setStatus(manifestacao.getStatus());

        // --- O SIGILO DO AUTOR DE MANIFESTAÇÃO ANÔNIMA VALE ATÉ PARA A EQUIPE ---
        if (!Boolean.TRUE.equals(manifestacao.getAnonima())) {
            item.setAutorNome(manifestacao.getAutor().getNome());
        }

        if (manifestacao.getResponsavel() != null) {
            item.setResponsavelNome(manifestacao.getResponsavel().getNome());
        }

        item.setPrazoResposta(manifestacao.getPrazoResposta());
        item.setCriadoEm(manifestacao.getCriadoEm());

        return item;
    }
}
