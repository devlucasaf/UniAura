package erp.uniaura.services.web.curso.removerCurso.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Curso;
import erp.uniaura.services.common.curso.BuscarCursoComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverCursoServiceV1 extends BaseService {

    private final BuscarCursoComponent buscarCursoComponent;

    // --- REMOVE O CURSO PELO UID ---
    @Transactional
    public void remover(RemoverCursoParamsV1 params) {
        Curso curso = buscarCursoComponent.buscarPorUid(params.getUid());
        curso.remove();
    }
}
