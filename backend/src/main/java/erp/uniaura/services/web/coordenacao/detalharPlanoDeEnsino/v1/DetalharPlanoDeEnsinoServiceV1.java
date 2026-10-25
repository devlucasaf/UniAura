package erp.uniaura.services.web.coordenacao.detalharPlanoDeEnsino.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.AvaliacaoPlanoEnsino;
import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarPlanoDeEnsinoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPermissaoDeEdicaoDoPlanoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalharPlanoDeEnsinoServiceV1 extends BaseService {

    private final BuscarPlanoDeEnsinoComponent buscarPlanoDeEnsinoComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarPermissaoDeEdicaoDoPlanoComponent verificarPermissaoDeEdicaoDoPlanoComponent;

    // --- DETALHA O PLANO DE ENSINO, SEUS DADOS E O HISTÓRICO DE AVALIAÇÕES ---
    @Transactional(readOnly = true)
    public DetalharPlanoDeEnsinoRetornoV1 detalhar(Long usuarioId, DetalharPlanoDeEnsinoParamsV1 params) {
        PlanoEnsino plano = buscarPlano(params);
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarPermissaoDeEdicaoDoPlanoComponent.validar(plano.getTurmaDisciplina(), autenticado);

        TurmaDisciplina turmaDisciplina = plano.getTurmaDisciplina();

        DetalharPlanoDeEnsinoRetornoV1 retorno = new DetalharPlanoDeEnsinoRetornoV1();

        retorno.setUid(plano.getUid());
        retorno.setTurmaDisciplinaUid(turmaDisciplina.getUid());
        retorno.setTurmaUid(turmaDisciplina.getTurma().getUid());
        retorno.setTurmaCodigo(turmaDisciplina.getTurma().getCodigo());
        retorno.setPeriodoLetivo(turmaDisciplina.getTurma().getPeriodoLetivo());
        retorno.setDisciplinaUid(turmaDisciplina.getDisciplina().getUid());
        retorno.setDisciplinaCodigo(turmaDisciplina.getDisciplina().getCodigo());
        retorno.setDisciplinaNome(turmaDisciplina.getDisciplina().getNome());
        retorno.setProfessorUid(plano.getProfessor().getUid());
        retorno.setEmenta(plano.getEmenta());
        retorno.setObjetivos(plano.getObjetivos());
        retorno.setConteudoProgramatico(plano.getConteudoProgramatico());
        retorno.setMetodologia(plano.getMetodologia());
        retorno.setCriteriosAvaliacao(plano.getCriteriosAvaliacao());
        retorno.setBibliografiaBasica(plano.getBibliografiaBasica());
        retorno.setBibliografiaComplementar(plano.getBibliografiaComplementar());
        retorno.setStatus(plano.getStatus());
        retorno.setParecer(plano.getParecer());
        retorno.setSubmetidoEm(plano.getSubmetidoEm());
        retorno.setAvaliadoEm(plano.getAvaliadoEm());
        retorno.setCriadoEm(plano.getCriadoEm());
        retorno.setAtualizadoEm(plano.getAtualizadoEm());

        if (plano.getProfessor().getUsuario() != null) {
            retorno.setProfessorNome(plano.getProfessor().getUsuario().getNome());
        }

        if (plano.getAvaliadoPor() != null) {
            retorno.setAvaliadoPorNome(plano.getAvaliadoPor().getNome());
        }

        retorno.setHistorico(montarHistorico(plano));

        return retorno;
    }

    // --- BUSCA O PLANO PELO UID OU, SEM ELE, PELO VÍNCULO TURMA/DISCIPLINA ---
    private PlanoEnsino buscarPlano(DetalharPlanoDeEnsinoParamsV1 params) {
        if (params.getUid() != null) {
            return buscarPlanoDeEnsinoComponent.buscarPorUid(params.getUid());
        }

        if (params.getTurmaDisciplinaUid() == null) {
            throw new BusinessException("Informe o uid do plano ou o uid do vínculo entre turma e disciplina.");
        }

        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        return buscarPlanoDeEnsinoComponent.buscarPorTurmaDisciplina(turmaDisciplina)
                .orElseThrow(() -> new BusinessException("Plano de ensino não encontrado para esta turma e disciplina."));
    }

    // --- MONTA O HISTÓRICO DE AVALIAÇÕES DO PLANO ORDENADO POR DATA ---
    private List<DetalharPlanoDeEnsinoHistoricoRetornoV1> montarHistorico(PlanoEnsino plano) {
        List<AvaliacaoPlanoEnsino> passos = em()
                .createQuery("select a from AvaliacaoPlanoEnsino a where a.planoEnsino = :plano order by a.criadoEm asc", AvaliacaoPlanoEnsino.class)
                .setParameter("plano", plano)
                .getResultList();

        List<DetalharPlanoDeEnsinoHistoricoRetornoV1> historico = new ArrayList<>();

        for (AvaliacaoPlanoEnsino passo : passos) {
            DetalharPlanoDeEnsinoHistoricoRetornoV1 item = new DetalharPlanoDeEnsinoHistoricoRetornoV1();

            item.setUid(passo.getUid());
            item.setStatusAnterior(passo.getStatusAnterior());
            item.setStatusNovo(passo.getStatusNovo());
            item.setParecer(passo.getParecer());
            item.setCriadoEm(passo.getCriadoEm());

            if (passo.getAutor() != null) {
                item.setAutorNome(passo.getAutor().getNome());
            }

            historico.add(item);
        }

        return historico;
    }
}
