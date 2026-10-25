package erp.uniaura.services.web.coordenacao.removerParticipanteDaReuniao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ParticipanteReuniao;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;
import erp.uniaura.services.common.coordenacao.VerificarReuniaoAgendadaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemoverParticipanteDaReuniaoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final VerificarReuniaoAgendadaComponent verificarReuniaoAgendadaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- SÓ SE REMOVE UM CONVOCADO ANTES DA REUNIÃO ACONTECER ---
    @Transactional
    public void remover(RemoverParticipanteDaReuniaoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getReuniaoUid());

        verificarReuniaoAgendadaComponent.validar(reuniao, "alterada");

        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUsuarioUid());

        List<ParticipanteReuniao> encontrados = em()
                .createQuery("select p from ParticipanteReuniao p where p.reuniao = :reuniao and p.usuario = :usuario", ParticipanteReuniao.class)
                .setParameter("reuniao", reuniao)
                .setParameter("usuario", usuario)
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("Participante não convocado para esta reunião.");
        }

        encontrados.get(0).remove();
    }
}
