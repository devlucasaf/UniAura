package erp.uniaura.services.web.coordenacao.avaliarPlanoDeEnsino.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.StatusPlanoEnsino;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarPlanoDeEnsinoComponent;
import erp.uniaura.services.common.coordenacao.RegistrarHistoricoDoPlanoComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AvaliarPlanoDeEnsinoServiceV1 extends BaseService {

    private static final Set<StatusPlanoEnsino> DESFECHOS = Set.of(
            StatusPlanoEnsino.APROVADO,
            StatusPlanoEnsino.DEVOLVIDO,
            StatusPlanoEnsino.REPROVADO
    );

    private final BuscarPlanoDeEnsinoComponent buscarPlanoDeEnsinoComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;
    private final RegistrarHistoricoDoPlanoComponent registrarHistoricoDoPlanoComponent;

    // --- A COORDENAÇÃO APROVA, DEVOLVE PARA AJUSTE OU REPROVA UM PLANO SUBMETIDO ---
    @Transactional
    public void avaliar(Long usuarioId, AvaliarPlanoDeEnsinoParamsV1 params) {
        PlanoEnsino plano = buscarPlanoDeEnsinoComponent.buscarPorUid(params.getUid());
        Usuario autenticado = buscarUsuarioComponent.buscarPorId(usuarioId);

        if (!DESFECHOS.contains(params.getStatus())) {
            throw new BusinessException("A avaliação deve resultar em APROVADO, DEVOLVIDO ou REPROVADO.");
        }

        if (plano.getStatus() != StatusPlanoEnsino.SUBMETIDO) {
            throw new BusinessException("Somente um plano SUBMETIDO pode ser avaliado. Status atual: %s.".formatted(plano.getStatus()));
        }

        boolean exigeParecer = params.getStatus() != StatusPlanoEnsino.APROVADO;

        if (exigeParecer && (params.getParecer() == null || params.getParecer().isBlank())) {
            throw new BusinessException("É obrigatório registrar o parecer ao devolver ou reprovar um plano de ensino.");
        }

        StatusPlanoEnsino anterior = plano.getStatus();

        plano.setStatus(params.getStatus());
        plano.setAvaliadoPor(autenticado);
        plano.setParecer(params.getParecer());
        plano.setAvaliadoEm(LocalDateTime.now());
        plano.merge();

        registrarHistoricoDoPlanoComponent.registrar(plano, autenticado, anterior, params.getStatus(), params.getParecer());
    }
}
