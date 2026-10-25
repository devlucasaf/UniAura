package erp.uniaura.services.web.turma.removerTurma.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Turma;
import erp.uniaura.services.common.turma.BuscarTurmaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverTurmaServiceV1 extends BaseService {

    private final BuscarTurmaComponent buscarTurmaComponent;

    @Transactional
    public void remover(RemoverTurmaParamsV1 params) {
        Turma turma = buscarTurmaComponent.buscarPorUid(params.getUid());
        turma.remove();
    }
}
