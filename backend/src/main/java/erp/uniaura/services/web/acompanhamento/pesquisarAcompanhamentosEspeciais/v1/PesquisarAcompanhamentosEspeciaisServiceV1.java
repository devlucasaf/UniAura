package erp.uniaura.services.web.acompanhamento.pesquisarAcompanhamentosEspeciais.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.AcompanhamentoEspecial;
import erp.uniaura.persistence.entities.Aluno;
import erp.uniaura.services.common.aluno.BuscarAlunoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarAcompanhamentosEspeciaisServiceV1 extends BaseService {

    private final BuscarAlunoComponent buscarAlunoComponent;

    // --- LISTA OS ACOMPANHAMENTOS DE FORMA PAGINADA, COM FILTROS OPCIONAIS DE STATUS, TIPO DE NECESSIDADE E ALUNO ---
    @Transactional(readOnly = true)
    public PesquisarAcompanhamentosEspeciaisRetornoV1 pesquisar(PesquisarAcompanhamentosEspeciaisParamsV1 params) {
        Aluno aluno = null;

        if (params.getAlunoUid() != null) {
            aluno = buscarAlunoComponent.buscarPorUid(params.getAlunoUid());
        }
        final Aluno alunoFiltrado = aluno;

        PageableQuery.Result<PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1> resultado =
                PageableQuery.<AcompanhamentoEspecial, PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1>builder()
                        .clazz(AcompanhamentoEspecial.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            if (params.getTipoNecessidade() != null) {
                                condicoes.add(cb().equal(from.get("tipoNecessidade"), params.getTipoNecessidade()));
                            }

                            if (alunoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("aluno"), alunoFiltrado));
                            }

                            if (!condicoes.isEmpty()) {
                                criteria.where(condicoes.toArray(new Predicate[0]));
                            }
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarAcompanhamentosEspeciaisRetornoV1 retorno = new PesquisarAcompanhamentosEspeciaisRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE A ENTIDADE NO ITEM DA LISTA ---
    private PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1 paraRetorno(AcompanhamentoEspecial acompanhamento) {
        PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1 item = new PesquisarAcompanhamentosEspeciaisAcompanhamentoRetornoV1();

        item.setUid(acompanhamento.getUid());
        item.setAlunoUid(acompanhamento.getAluno().getUid());
        item.setAlunoNome(acompanhamento.getAluno().getUsuario().getNome());
        item.setMatriculaRA(acompanhamento.getAluno().getMatriculaRA());
        item.setTipoNecessidade(acompanhamento.getTipoNecessidade());
        item.setStatus(acompanhamento.getStatus());
        item.setPossuiLaudo(acompanhamento.getPossuiLaudo());
        item.setDataInicio(acompanhamento.getDataInicio());

        return item;
    }
}
