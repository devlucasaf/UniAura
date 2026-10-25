package erp.uniaura.services.web.coordenacao.submeterPlanoDeEnsino.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.StatusPlanoEnsino;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarPlanoDeEnsinoComponent;
import erp.uniaura.services.common.coordenacao.RegistrarHistoricoDoPlanoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPermissaoDeEdicaoDoPlanoComponent;
import erp.uniaura.services.common.coordenacao.VerificarPlanoEditavelComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubmeterPlanoDeEnsinoServiceV1 extends BaseService {

    private final BuscarPlanoDeEnsinoComponent buscarPlanoDeEnsinoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final VerificarPermissaoDeEdicaoDoPlanoComponent verificarPermissaoDeEdicaoDoPlanoComponent;
    private final VerificarPlanoEditavelComponent verificarPlanoEditavelComponent;
    private final RegistrarHistoricoDoPlanoComponent registrarHistoricoDoPlanoComponent;

    // --- O PROFESSOR ENVIA O PLANO PARA A AVALIAÇÃO DA COORDENAÇÃO ---
    @Transactional
    public void submeter(Long usuarioId, SubmeterPlanoDeEnsinoParamsV1 params) {
        PlanoEnsino plano = buscarPlanoDeEnsinoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        verificarPermissaoDeEdicaoDoPlanoComponent.validar(plano.getTurmaDisciplina(), autenticado);
        verificarPlanoEditavelComponent.validar(plano, "submetido");

        StatusPlanoEnsino anterior = plano.getStatus();

        plano.setStatus(StatusPlanoEnsino.SUBMETIDO);
        plano.setSubmetidoEm(LocalDateTime.now());
        plano.merge();

        registrarHistoricoDoPlanoComponent.registrar(plano, autenticado, anterior, StatusPlanoEnsino.SUBMETIDO,
                "Plano submetido para avaliação da coordenação.");
    }
}
