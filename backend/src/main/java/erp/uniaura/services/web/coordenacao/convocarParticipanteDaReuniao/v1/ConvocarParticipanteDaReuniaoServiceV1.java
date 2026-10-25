package erp.uniaura.services.web.coordenacao.convocarParticipanteDaReuniao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.ParticipanteReuniao;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.persistence.entities.Usuario;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;
import erp.uniaura.services.common.coordenacao.VerificarReuniaoAgendadaComponent;
import erp.uniaura.services.common.usuario.BuscarUsuarioComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConvocarParticipanteDaReuniaoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final VerificarReuniaoAgendadaComponent verificarReuniaoAgendadaComponent;
    private final BuscarUsuarioComponent buscarUsuarioComponent;

    // --- CADA USUÁRIO É CONVOCADO UMA ÚNICA VEZ POR REUNIÃO ---
    @Transactional
    public Uid convocar(ConvocarParticipanteDaReuniaoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getReuniaoUid());

        verificarReuniaoAgendadaComponent.validar(reuniao, "alterada");

        Usuario usuario = buscarUsuarioComponent.buscarPorUid(params.getUsuarioUid());

        Long convocacoes = em()
                .createQuery("select count(p) from ParticipanteReuniao p where p.reuniao = :reuniao and p.usuario = :usuario", Long.class)
                .setParameter("reuniao", reuniao)
                .setParameter("usuario", usuario)
                .getSingleResult();

        if (convocacoes > 0) {
            throw new BusinessException("Este participante já foi convocado para a reunião.");
        }

        ParticipanteReuniao participante = new ParticipanteReuniao();

        participante.setReuniao(reuniao);
        participante.setUsuario(usuario);
        participante.setPapel(params.getPapel());
        participante.persist();

        return new Uid(participante.getUid());
    }
}
