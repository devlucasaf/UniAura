package erp.uniaura.services.web.financeiro.pesquisarMinhasMensalidades.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.persistence.entities.Mensalidade;
import erp.uniaura.persistence.entities.Pagamento;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;
import erp.uniaura.services.common.financeiro.BuscarPagamentoDaMensalidadeComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PesquisarMinhasMensalidadesServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;
    private final BuscarPagamentoDaMensalidadeComponent buscarPagamentoDaMensalidadeComponent;

    // --- PESQUISA AS MENSALIDADES DO ALUNO LOGADO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMinhasMensalidadesRetornoV1 pesquisar(Long usuarioId, PesquisarMinhasMensalidadesParamsV1 params) {
        Aluno aluno = buscarAlunoComponent.buscarPorUsuarioId(usuarioId);

        PageableQuery.Result<PesquisarMinhasMensalidadesMensalidadeRetornoV1> resultado =
                PageableQuery.<Mensalidade, PesquisarMinhasMensalidadesMensalidadeRetornoV1>builder()
                        .clazz(Mensalidade.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("aluno"), aluno)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMinhasMensalidadesRetornoV1 retorno = new PesquisarMinhasMensalidadesRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A MENSALIDADE NO ITEM DE RETORNO COM OS DADOS DO PAGAMENTO ---
    private PesquisarMinhasMensalidadesMensalidadeRetornoV1 paraRetorno(Mensalidade mensalidade) {
        PesquisarMinhasMensalidadesMensalidadeRetornoV1 item = new PesquisarMinhasMensalidadesMensalidadeRetornoV1();

        item.setUid(mensalidade.getUid());
        item.setCompetencia(mensalidade.getCompetencia());
        item.setValor(mensalidade.getValor());
        item.setVencimento(mensalidade.getVencimento());
        item.setStatus(mensalidade.getStatus());
        item.setDescricao(mensalidade.getDescricao());

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
