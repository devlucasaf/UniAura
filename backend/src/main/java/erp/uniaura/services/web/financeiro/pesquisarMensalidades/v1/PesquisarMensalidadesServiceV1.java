package erp.uniaura.services.web.financeiro.pesquisarMensalidades.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.Pagamento;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.financeiro.BuscarPagamentoDaMensalidadeComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PesquisarMensalidadesServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarPagamentoDaMensalidadeComponent buscarPagamentoDaMensalidadeComponent;

    // --- PESQUISA MENSALIDADES COM PAGINAÇÃO E FILTROS POR ALUNO E STATUS ---
    @Transactional(readOnly = true)
    public PesquisarMensalidadesRetornoV1 pesquisar(PesquisarMensalidadesParamsV1 params) {
        Aluno aluno = null;

        if (params.getAlunoUid() != null) {
            aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        }

        final Aluno alunoFiltrado = aluno;

        PageableQuery.Result<PesquisarMensalidadesMensalidadeRetornoV1> resultado =
                PageableQuery.<Mensalidade, PesquisarMensalidadesMensalidadeRetornoV1>builder()
                        .clazz(Mensalidade.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (alunoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("aluno"), alunoFiltrado));
                            }

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMensalidadesRetornoV1 retorno = new PesquisarMensalidadesRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MENSALIDADE NO ITEM DE RETORNO COM OS DADOS DO PAGAMENTO ---
    private PesquisarMensalidadesMensalidadeRetornoV1 paraRetorno(Mensalidade mensalidade) {
        PesquisarMensalidadesMensalidadeRetornoV1 item = new PesquisarMensalidadesMensalidadeRetornoV1();

        item.setUid(mensalidade.getUid());
        item.setAlunoUid(mensalidade.getAluno().getUid());
        item.setCompetencia(mensalidade.getCompetencia());
        item.setValor(mensalidade.getValor());
        item.setVencimento(mensalidade.getVencimento());
        item.setStatus(mensalidade.getStatus());
        item.setDescricao(mensalidade.getDescricao());

        if (mensalidade.getAluno().getUsuario() != null) {
            item.setAlunoNome(mensalidade.getAluno().getUsuario().getNome());
        }

        Optional<Pagamento> pagamento = buscarPagamentoDaMensalidadeComponent.buscar(mensalidade);

        if (pagamento.isPresent()) {
            item.setFormaPagamento(pagamento.get().getFormaPagamento());
            item.setValorPago(pagamento.get().getValorPago());
            item.setValorMulta(pagamento.get().getValorMulta());
            item.setDataPagamento(pagamento.get().getDataPagamento());
        }

        return item;
    }
}
