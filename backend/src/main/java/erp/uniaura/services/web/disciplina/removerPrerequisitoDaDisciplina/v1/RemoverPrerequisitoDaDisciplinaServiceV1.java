package erp.uniaura.services.web.disciplina.removerPrerequisitoDaDisciplina.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.DisciplinaPrerequisito;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverPrerequisitoDaDisciplinaServiceV1 extends BaseService {

    // --- REMOVE O VÍNCULO DE PRÉ-REQUISITO DA DISCIPLINA ---
    @Transactional
    public void remover(RemoverPrerequisitoDaDisciplinaParamsV1 params) {
        DisciplinaPrerequisito vinculo = em()
                .createQuery("select v from DisciplinaPrerequisito v "
                        + "where v.disciplina.uid = :disciplinaUid and v.prerequisito.uid = :prerequisitoUid", DisciplinaPrerequisito.class)
                .setParameter("disciplinaUid", params.getDisciplinaUid())
                .setParameter("prerequisitoUid", params.getPrerequisitoUid())
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Pré-requisito não encontrado nesta disciplina."));

        vinculo.remove();
    }
}
