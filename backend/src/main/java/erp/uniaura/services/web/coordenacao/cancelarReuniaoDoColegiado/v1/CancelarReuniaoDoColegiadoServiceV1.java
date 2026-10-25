package erp.uniaura.services.web.coordenacao.cancelarReuniaoDoColegiado.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.persistence.entities.StatusReuniao;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;
import erp.uniaura.services.common.coordenacao.VerificarReuniaoAgendadaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CancelarReuniaoDoColegiadoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final VerificarReuniaoAgendadaComponent verificarReuniaoAgendadaComponent;

    // --- CANCELA A REUNIÃO AGENDADA, GRAVANDO O MOTIVO E A DATA ---
    @Transactional
    public void cancelar(CancelarReuniaoDoColegiadoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getUid());

        verificarReuniaoAgendadaComponent.validar(reuniao, "cancelada");

        reuniao.setStatus(StatusReuniao.CANCELADA);
        reuniao.setMotivoCancelamento(params.getMotivo());
        reuniao.setEncerradaEm(LocalDateTime.now());
        reuniao.merge();
    }
}
