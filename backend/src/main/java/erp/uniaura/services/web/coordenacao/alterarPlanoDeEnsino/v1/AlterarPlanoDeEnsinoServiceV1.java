package erp.uniaura.services.web.coordenacao.alterarPlanoDeEnsino.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarPlanoDeEnsinoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPermissaoDeEdicaoDoPlanoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPlanoEditavelComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarPlanoDeEnsinoServiceV1 extends BaseService {

    private final BuscarPlanoDeEnsinoComponent buscarPlanoDeEnsinoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarPermissaoDeEdicaoDoPlanoComponent verificarPermissaoDeEdicaoDoPlanoComponent;
    private final VerificarPlanoEditavelComponent verificarPlanoEditavelComponent;

    // --- ALTERA O PLANO DE ENSINO APÓS VALIDAR PERMISSÃO E SE AINDA É EDITÁVEL ---
    @Transactional
    public void alterar(Long usuarioId, AlterarPlanoDeEnsinoParamsV1 params) {
        PlanoEnsino plano = buscarPlanoDeEnsinoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarPermissaoDeEdicaoDoPlanoComponent.validar(plano.getTurmaDisciplina(), autenticado);
        verificarPlanoEditavelComponent.validar(plano, "editado");

        plano.setEmenta(params.getEmenta());
        plano.setObjetivos(params.getObjetivos());
        plano.setConteudoProgramatico(params.getConteudoProgramatico());
        plano.setMetodologia(params.getMetodologia());
        plano.setCriteriosAvaliacao(params.getCriteriosAvaliacao());
        plano.setBibliografiaBasica(params.getBibliografiaBasica());
        plano.setBibliografiaComplementar(params.getBibliografiaComplementar());
        plano.merge();
    }
}
