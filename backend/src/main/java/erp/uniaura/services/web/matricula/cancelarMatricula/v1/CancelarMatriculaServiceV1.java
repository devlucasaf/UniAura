package erp.uniaura.services.web.matricula.cancelarMatricula.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.StatusMatricula;
import erp.uniaura.services.common.matricula.AlterarStatusDaMatriculaComponent;
import erp.uniaura.services.common.matricula.BuscarMatriculaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelarMatriculaServiceV1 extends BaseService {

    private final BuscarMatriculaComponent buscarMatriculaComponent;
    private final AlterarStatusDaMatriculaComponent alterarStatusDaMatriculaComponent;

    // --- CANCELA A MATRÍCULA ALTERANDO SEU STATUS E GRAVANDO AS OBSERVAÇÕES ---
    @Transactional
    public void cancelar(CancelarMatriculaParamsV1 params) {
        alterarStatusDaMatriculaComponent.alterar(
                buscarMatriculaComponent.buscarPorUid(params.getUid()), StatusMatricula.CANCELADA, params.getObservacoes());
    }
}
