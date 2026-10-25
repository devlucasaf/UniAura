package erp.uniaura.services.web.atividade.pesquisarAtividadesDaTurmaDisciplina.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Atividade;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarAtividadesDaTurmaDisciplinaServiceV1 extends BaseService {

    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;

    // --- LISTA AS ATIVIDADES DE UMA TURMA E DISCIPLINA DE FORMA PAGINADA ---
    @Transactional(readOnly = true)
    public PesquisarAtividadesDaTurmaDisciplinaRetornoV1 pesquisar(PesquisarAtividadesDaTurmaDisciplinaParamsV1 params) {
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        PageableQuery.Result<PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1> resultado =
                PageableQuery.<Atividade, PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1>builder()
                        .clazz(Atividade.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("turmaDisciplina"), turmaDisciplina)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarAtividadesDaTurmaDisciplinaRetornoV1 retorno = new PesquisarAtividadesDaTurmaDisciplinaRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A ENTIDADE NO ITEM DA LISTA ---
    private PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1 paraRetorno(Atividade atividade) {
        PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1 item = new PesquisarAtividadesDaTurmaDisciplinaAtividadeRetornoV1();

        item.setUid(atividade.getUid());
        item.setTitulo(atividade.getTitulo());
        item.setTipo(atividade.getTipo());
        item.setDataPostagem(atividade.getDataPostagem());
        item.setDataEntrega(atividade.getDataEntrega());
        item.setValorMaximo(atividade.getValorMaximo());
        item.setProfessorNome(atividade.getProfessor().getUsuario().getNome());
        item.setAtiva(atividade.getAtiva());

        return item;
    }
}
