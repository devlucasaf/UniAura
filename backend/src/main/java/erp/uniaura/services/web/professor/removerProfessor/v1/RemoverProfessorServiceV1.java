package erp.uniaura.services.web.professor.removerProfessor.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Professor;
import erp.uniaura.services.common.professor.BuscarProfessorComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverProfessorServiceV1 extends BaseService {

    private final BuscarProfessorComponent buscarProfessorComponent;

    // --- REMOVE O PROFESSOR PELO UID ---
    @Transactional
    public void remover(RemoverProfessorParamsV1 params) {
        Professor professor = buscarProfessorComponent.buscarPorUid(params.getUid());
        professor.remove();
    }
}
