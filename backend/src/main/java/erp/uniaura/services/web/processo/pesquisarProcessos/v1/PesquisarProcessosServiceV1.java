package erp.uniaura.services.web.processo.pesquisarProcessos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Processo;

import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PesquisarProcessosServiceV1 extends BaseService {

    // --- PESQUISA PROCESSOS COM PAGINAÇÃO E FILTROS POR STATUS E TIPO ---
    @Transactional(readOnly = true)
    public PesquisarProcessosRetornoV1 pesquisar(PesquisarProcessosParamsV1 params) {
        PageableQuery.Result<PesquisarProcessosProcessoRetornoV1> resultado =
                PageableQuery.<Processo, PesquisarProcessosProcessoRetornoV1>builder()
                        .clazz(Processo.class)
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

        PesquisarProcessosRetornoV1 retorno = new PesquisarProcessosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O PROCESSO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarProcessosProcessoRetornoV1 paraRetorno(Processo processo) {
        PesquisarProcessosProcessoRetornoV1 item = new PesquisarProcessosProcessoRetornoV1();

        item.setUid(processo.getUid());
        item.setProtocolo(processo.getProtocolo());
        item.setAlunoNome(processo.getAluno().getUsuario().getNome());
        item.setAlunoMatriculaRA(processo.getAluno().getMatriculaRA());
        item.setTipo(processo.getTipo());
        item.setAssunto(processo.getAssunto());
        item.setStatus(processo.getStatus());

        if (processo.getResponsavel() != null) {
            item.setResponsavelNome(processo.getResponsavel().getNome());
        }

        item.setPrazoResposta(processo.getPrazoResposta());
        item.setCriadoEm(processo.getCriadoEm());

        return item;
    }
}
