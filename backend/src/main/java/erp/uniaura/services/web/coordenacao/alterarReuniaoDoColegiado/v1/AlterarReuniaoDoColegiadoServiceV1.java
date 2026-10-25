package erp.uniaura.services.web.coordenacao.alterarReuniaoDoColegiado.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.ReuniaoColegiado;
import erp.uniaura.services.common.coordenacao.BuscarReuniaoDoColegiadoComponent;
import erp.uniaura.services.common.coordenacao.VerificarReuniaoAgendadaComponent;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlterarReuniaoDoColegiadoServiceV1 extends BaseService {

    private final BuscarReuniaoDoColegiadoComponent buscarReuniaoDoColegiadoComponent;
    private final VerificarReuniaoAgendadaComponent verificarReuniaoAgendadaComponent;
    private final BuscarCursoComponent buscarCursoComponent;

    // --- ALTERA A REUNIÃO DO COLEGIADO, SE AINDA ESTIVER AGENDADA ---
    @Transactional
    public void alterar(AlterarReuniaoDoColegiadoParamsV1 params) {
        ReuniaoColegiado reuniao = buscarReuniaoDoColegiadoComponent.buscarPorUid(params.getUid());

        verificarReuniaoAgendadaComponent.validar(reuniao, "alterada");

        if (!reuniao.getCurso().getUid().equals(params.getCursoUid())) {
            reuniao.setCurso(buscarCursoComponent.buscarPorUid(params.getCursoUid()));
        }

        reuniao.setTitulo(params.getTitulo());
        reuniao.setDataHora(params.getDataHora());
        reuniao.setLocal(params.getLocal());
        reuniao.setPauta(params.getPauta());
        reuniao.merge();
    }
}
