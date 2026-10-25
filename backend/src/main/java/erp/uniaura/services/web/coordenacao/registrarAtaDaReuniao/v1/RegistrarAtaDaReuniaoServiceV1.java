package erp.uniaura.services.web.coordenacao.registrarAtaDaReuniao.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.ParticipanteReuniao;
import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.persistence.entities.StatusReuniao;
import erp.uniaura.services.common.coordenacao.BuscarParticipantesDaReuniaoComponent;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;
import erp.uniaura.services.common.coordenacao.VerificarReuniaoAgendadaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrarAtaDaReuniaoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final VerificarReuniaoAgendadaComponent verificarReuniaoAgendadaComponent;
    private final BuscarParticipantesDaReuniaoComponent buscarParticipantesDaReuniaoComponent;

    // --- REGISTRA A ATA, MARCA AS PRESENÇAS E ENCERRA A REUNIÃO ---
    @Transactional
    public void registrar(RegistrarAtaDaReuniaoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getReuniaoUid());

        verificarReuniaoAgendadaComponent.validar(reuniao, "encerrada");

        List<ParticipanteReuniao> participantes = buscarParticipantesDaReuniaoComponent.buscar(reuniao);

        if (participantes.isEmpty()) {
            throw new BusinessException("Não é possível registrar a ata de uma reunião sem participantes convocados.");
        }

        Map<Long, Boolean> presencas = new HashMap<>();

        if (params.getPresencas() != null) {
            for (RegistrarAtaDaReuniaoPresencaParamsV1 presenca : params.getPresencas()) {
                presencas.put(presenca.getUsuarioUid(), presenca.getPresente());
            }
        }

        for (ParticipanteReuniao participante : participantes) {
            participante.setPresente(presencas.getOrDefault(participante.getUsuario().getUid(), Boolean.FALSE));
            participante.merge();
        }

        reuniao.setDeliberacoes(params.getDeliberacoes());
        reuniao.setStatus(StatusReuniao.REALIZADA);
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniao.merge();
    }
}
