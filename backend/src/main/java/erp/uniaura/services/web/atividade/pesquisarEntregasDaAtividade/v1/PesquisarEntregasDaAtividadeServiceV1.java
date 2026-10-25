package erp.uniaura.services.web.atividade.pesquisarEntregasDaAtividade.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.EntregaAtividade;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.atividade.BuscarAtividadeComponent;
import erp.uniaura.services.common.atividade.VerificarAutorizacaoDaAtividadeComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarEntregasDaAtividadeServiceV1 extends BaseService {

    private final BuscarAtividadeComponent buscarAtividadeComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarAutorizacaoDaAtividadeComponent verificarAutorizacaoDaAtividadeComponent;

    // --- PESQUISA ENTREGAS DA ATIVIDADE COM PAGINAÇÃO E FILTRO POR STATUS ---
    @Transactional(readOnly = true)
    public PesquisarEntregasDaAtividadeRetornoV1 pesquisar(Long usuarioId, PesquisarEntregasDaAtividadeParamsV1 params) {
        Atividade atividade = buscarAtividadeComponent.buscarPorUid(params.getAtividadeUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarAutorizacaoDaAtividadeComponent.validarPodeEditar(atividade, autenticado);

        PageableQuery.Result<PesquisarEntregasDaAtividadeEntregaRetornoV1> resultado =
                PageableQuery.<EntregaAtividade, PesquisarEntregasDaAtividadeEntregaRetornoV1>builder()
                        .clazz(EntregaAtividade.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            condicoes.add(cb().equal(from.get("atividade"), atividade));

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarEntregasDaAtividadeRetornoV1 retorno = new PesquisarEntregasDaAtividadeRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A ENTREGA NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarEntregasDaAtividadeEntregaRetornoV1 paraRetorno(EntregaAtividade entrega) {
        PesquisarEntregasDaAtividadeEntregaRetornoV1 item = new PesquisarEntregasDaAtividadeEntregaRetornoV1();

        item.setUid(entrega.getUid());
        item.setAlunoUid(entrega.getAluno().getUid());
        item.setAlunoNome(entrega.getAluno().getUsuario().getNome());
        item.setArquivoUrl(entrega.getArquivoUrl());
        item.setComentarioAluno(entrega.getComentarioAluno());
        item.setDataEntrega(entrega.getDataEntrega());
        item.setNota(entrega.getNota());
        item.setFeedback(entrega.getFeedback());
        item.setStatus(entrega.getStatus());

        return item;
    }
}
