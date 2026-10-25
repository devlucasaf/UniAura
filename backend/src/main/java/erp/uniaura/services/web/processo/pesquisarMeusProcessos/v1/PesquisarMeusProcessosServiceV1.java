package erp.uniaura.services.web.processo.pesquisarMeusProcessos.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Processo;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarMeusProcessosServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- PESQUISA OS PROCESSOS DO ALUNO LOGADO COM PAGINAÇÃO E FILTRO POR STATUS ---
    @Transactional(readOnly = true)
    public PesquisarMeusProcessosRetornoV1 pesquisar(Long usuarioId, PesquisarMeusProcessosParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        PageableQuery.Result<PesquisarMeusProcessosProcessoRetornoV1> resultado =
                PageableQuery.<Processo, PesquisarMeusProcessosProcessoRetornoV1>builder()
                        .clazz(Processo.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("aluno"), aluno));

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMeusProcessosRetornoV1 retorno = new PesquisarMeusProcessosRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O PROCESSO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMeusProcessosProcessoRetornoV1 paraRetorno(Processo processo) {
        PesquisarMeusProcessosProcessoRetornoV1 item = new PesquisarMeusProcessosProcessoRetornoV1();

        item.setUid(processo.getUid());
        item.setProtocolo(processo.getProtocolo());
        item.setTipo(processo.getTipo());
        item.setAssunto(processo.getAssunto());
        item.setStatus(processo.getStatus());
        item.setPrazoResposta(processo.getPrazoResposta());
        item.setCriadoEm(processo.getCriadoEm());

        return item;
    }
}
