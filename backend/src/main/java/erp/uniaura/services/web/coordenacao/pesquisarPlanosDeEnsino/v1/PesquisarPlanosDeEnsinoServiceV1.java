package erp.uniaura.services.web.coordenacao.pesquisarPlanosDeEnsino.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import jakarta.persistence.criteria.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisarPlanosDeEnsinoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- PESQUISA PLANOS DE ENSINO COM PAGINAÇÃO E FILTROS POR STATUS, CURSO E PERÍODO ---
    @Transactional(readOnly = true)
    public PesquisarPlanosDeEnsinoRetornoV1 pesquisar(PesquisarPlanosDeEnsinoParamsV1 params) {
        Curso curso = null;

        if (params.getCursoUid() != null) {
            curso = buscarCursoComponent.buscarPorUid(params.getCursoUid());
        }

        final Curso cursoFiltrado = curso;

        PageableQuery.Result<PesquisarPlanosDeEnsinoPlanoRetornoV1> resultado =
                PageableQuery.<PlanoEnsino, PesquisarPlanosDeEnsinoPlanoRetornoV1>builder()
                        .clazz(PlanoEnsino.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> {
                            List<Predicate> condicoes = new ArrayList<>();

                            if (params.getStatus() != null) {
                                condicoes.add(cb().equal(from.get("status"), params.getStatus()));
                            }

                            if (cursoFiltrado != null) {
                                condicoes.add(cb().equal(from.get("turmaDisciplina").get("turma").get("curso"), cursoFiltrado));
                            }

                            if (params.getPeriodoLetivo() != null && !params.getPeriodoLetivo().isBlank()) {
                                condicoes.add(cb().equal(from.get("turmaDisciplina").get("turma").get("periodoLetivo"), params.getPeriodoLetivo()));
                            }

                            criteria.where(condicoes.toArray(new Predicate[0]));
                        })
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarPlanosDeEnsinoRetornoV1 retorno = new PesquisarPlanosDeEnsinoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O PLANO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarPlanosDeEnsinoPlanoRetornoV1 paraRetorno(PlanoEnsino plano) {
        TurmaDisciplina turmaDisciplina = plano.getTurmaDisciplina();

        PesquisarPlanosDeEnsinoPlanoRetornoV1 item = new PesquisarPlanosDeEnsinoPlanoRetornoV1();

        item.setUid(plano.getUid());
        item.setTurmaDisciplinaUid(turmaDisciplina.getUid());
        item.setTurmaUid(turmaDisciplina.getTurma().getUid());
        item.setTurmaCodigo(turmaDisciplina.getTurma().getCodigo());
        item.setPeriodoLetivo(turmaDisciplina.getTurma().getPeriodoLetivo());
        item.setDisciplinaUid(turmaDisciplina.getDisciplina().getUid());
        item.setDisciplinaCodigo(turmaDisciplina.getDisciplina().getCodigo());
        item.setDisciplinaNome(turmaDisciplina.getDisciplina().getNome());
        item.setProfessorUid(plano.getProfessor().getUid());
        item.setStatus(plano.getStatus());
        item.setParecer(plano.getParecer());
        item.setSubmetidoEm(plano.getSubmetidoEm());
        item.setAvaliadoEm(plano.getAvaliadoEm());
        item.setAtualizadoEm(plano.getAtualizadoEm());

        if (plano.getProfessor().getUsuario() != null) {
            item.setProfessorNome(plano.getProfessor().getUsuario().getNome());
        }

        return item;
    }
}
