package erp.uniaura.services.web.coordenacao.pesquisarMeusPlanosDeEnsino.v1;

import cloudsupport.persistence.PageableQuery;
import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PesquisarMeusPlanosDeEnsinoServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- PESQUISA OS PLANOS DE ENSINO DO PROFESSOR LOGADO COM PAGINAÇÃO ---
    @Transactional(readOnly = true)
    public PesquisarMeusPlanosDeEnsinoRetornoV1 pesquisar(Long usuarioId, PesquisarMeusPlanosDeEnsinoParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUsuarioId(usuarioId)
                .orElseThrow(() -> new BusinessException("O usuário autenticado não possui cadastro de professor."));

        PageableQuery.Result<PesquisarMeusPlanosDeEnsinoPlanoRetornoV1> resultado =
                PageableQuery.<PlanoEnsino, PesquisarMeusPlanosDeEnsinoPlanoRetornoV1>builder()
                        .clazz(PlanoEnsino.class)
                        .entityManager(em())
                        .first(params.getDe())
                        .limit(params.getQtd())
                        .order(params.getOrdem())
                        .predicate((criteria, from) -> criteria.where(cb().equal(from.get("professor"), professor)))
                        .converter(this::paraRetorno)
                        .build()
                        .query();

        PesquisarMeusPlanosDeEnsinoRetornoV1 retorno = new PesquisarMeusPlanosDeEnsinoRetornoV1();

        retorno.setTotal(resultado.getTotal());
        retorno.setLista(resultado.getList());

        return retorno;
    }

    // --- CONVERTE O PLANO NO ITEM DE RETORNO DA PESQUISA ---
    private PesquisarMeusPlanosDeEnsinoPlanoRetornoV1 paraRetorno(PlanoEnsino plano) {
        TurmaDisciplina turmaDisciplina = plano.getTurmaDisciplina();

        PesquisarMeusPlanosDeEnsinoPlanoRetornoV1 item = new PesquisarMeusPlanosDeEnsinoPlanoRetornoV1();

        item.setUid(plano.getUid());
        item.setTurmaDisciplinaUid(turmaDisciplina.getUid());
        item.setTurmaUid(turmaDisciplina.getTurma().getUid());
        item.setTurmaCodigo(turmaDisciplina.getTurma().getCodigo());
        item.setPeriodoLetivo(turmaDisciplina.getTurma().getPeriodoLetivo());
        item.setDisciplinaUid(turmaDisciplina.getDisciplina().getUid());
        item.setDisciplinaCodigo(turmaDisciplina.getDisciplina().getCodigo());
        item.setDisciplinaNome(turmaDisciplina.getDisciplina().getNome());
        item.setStatus(plano.getStatus());
        item.setParecer(plano.getParecer());
        item.setSubmetidoEm(plano.getSubmetidoEm());
        item.setAvaliadoEm(plano.getAvaliadoEm());
        item.setAtualizadoEm(plano.getAtualizadoEm());

        return item;
    }
}
