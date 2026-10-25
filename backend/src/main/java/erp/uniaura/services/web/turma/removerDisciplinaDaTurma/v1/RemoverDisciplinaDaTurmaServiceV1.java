package erp.uniaura.services.web.turma.removerDisciplinaDaTurma.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoverDisciplinaDaTurmaServiceV1 extends BaseService {

    @Transactional
    public void remover(RemoverDisciplinaDaTurmaParamsV1 params) {
        TurmaDisciplina vinculo = em()
                .createQuery("select v from TurmaDisciplina v where v.turma.uid = :turmaUid and v.disciplina.uid = :disciplinaUid", TurmaDisciplina.class)
                .setParameter("turmaUid", params.getTurmaUid())
                .setParameter("disciplinaUid", params.getDisciplinaUid())
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new BusinessException("Esta disciplina não está vinculada à turma."));

        vinculo.remove();
    }
}
