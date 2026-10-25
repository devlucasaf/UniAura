package erp.uniaura.services.web.disciplina.removerDisciplina.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverDisciplinaServiceV1 extends BaseService {

    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- REMOVE A DISCIPLINA PELO UID ---
    @Transactional
    public void remover(RemoverDisciplinaParamsV1 params) {
        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getUid());
        disciplina.remove();
    }
}
