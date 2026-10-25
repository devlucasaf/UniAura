package erp.uniaura.services.web.coordenacao.cadastrarPlanoDeEnsino.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.StatusPlanoEnsino;
import erp.uniaura.persistence.entities.TurmaDisciplina;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarPlanoDeEnsinoComponent;
import erp.uniaura.services.common.coordenacao.RegistrarHistoricoDoPlanoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPermissaoDeEdicaoDoPlanoComponent;
import erp.uniaura.services.common.turma.BuscarTurmaDisciplinaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CadastrarPlanoDeEnsinoServiceV1 extends BaseService {

    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final BuscarTurmaDisciplinaComponent buscarTurmaDisciplinaComponent;
    private final BuscarPlanoDeEnsinoComponent buscarPlanoDeEnsinoComponent;
    private final VerificarPermissaoDeEdicaoDoPlanoComponent verificarPermissaoDeEdicaoDoPlanoComponent;
    private final RegistrarHistoricoDoPlanoComponent registrarHistoricoDoPlanoComponent;

    // --- O PLANO NASCE EM RASCUNHO, COM O PROFESSOR RESPONSÁVEL PELA TURMA E DISCIPLINA ---
    @Transactional
    public Uid cadastrar(Long usuarioId, CadastrarPlanoDeEnsinoParamsV1 params) {
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);
        TurmaDisciplina turmaDisciplina = buscarTurmaDisciplinaComponent.buscarPorUid(params.getTurmaDisciplinaUid());

        if (buscarPlanoDeEnsinoComponent.buscarPorTurmaDisciplina(turmaDisciplina).isPresent()) {
            throw new BusinessException("Já existe um plano de ensino para esta turma/disciplina.");
        }

        verificarPermissaoDeEdicaoDoPlanoComponent.validar(turmaDisciplina, autenticado);

        PlanoEnsino plano = new PlanoEnsino();

        plano.setTurmaDisciplina(turmaDisciplina);
        plano.setProfessor(turmaDisciplina.getProfessor());
        plano.setStatus(StatusPlanoEnsino.RASCUNHO);
        plano.setEmenta(params.getEmenta());
        plano.setObjetivos(params.getObjetivos());
        plano.setConteudoProgramatico(params.getConteudoProgramatico());
        plano.setMetodologia(params.getMetodologia());
        plano.setCriteriosAvaliacao(params.getCriteriosAvaliacao());
        plano.setBibliografiaBasica(params.getBibliografiaBasica());
        plano.setBibliografiaComplementar(params.getBibliografiaComplementar());
        plano.persist();

        registrarHistoricoDoPlanoComponent.registrar(plano, autenticado, null, StatusPlanoEnsino.RASCUNHO, "Plano de ensino criado.");

        return new Uid(plano.getUid());
    }
}
