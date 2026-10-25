package erp.uniaura.services.web.disciplina.adicionarPrerequisitoDaDisciplina.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;
import cloudsupport.services.Uid;

import erp.uniaura.persistence.entities.Disciplina;
import erp.uniaura.persistence.entities.DisciplinaPrerequisito;
import erp.uniaura.services.common.disciplina.BuscarDisciplinaComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdicionarPrerequisitoDaDisciplinaServiceV1 extends BaseService {

    private final BuscarDisciplinaComponent buscarDisciplinaComponent;

    // --- VINCULA UM PRÉ-REQUISITO À DISCIPLINA, VALIDANDO DUPLICIDADE E AUTOVÍNCULO ---
    @Transactional
    public Uid adicionar(AdicionarPrerequisitoDaDisciplinaParamsV1 params) {
        if (params.getDisciplinaUid().equals(params.getPrerequisitoUid())) {
            throw new BusinessException("Uma disciplina não pode ser pré-requisito de si mesma.");
        }

        Disciplina disciplina = buscarDisciplinaComponent.buscarPorUid(params.getDisciplinaUid());
        Disciplina prerequisito = buscarDisciplinaComponent.buscarPorUid(params.getPrerequisitoUid());

        Long jaVinculados = em()
                .createQuery("select count(v) from DisciplinaPrerequisito v "
                        + "where v.disciplina = :disciplina and v.prerequisito = :prerequisito", Long.class)
                .setParameter("disciplina", disciplina)
                .setParameter("prerequisito", prerequisito)
                .getSingleResult();
        if (jaVinculados > 0) {
            throw new BusinessException("Este pré-requisito já está vinculado à disciplina.");
        }

        DisciplinaPrerequisito vinculo = new DisciplinaPrerequisito();

        vinculo.setDisciplina(disciplina);
        vinculo.setPrerequisito(prerequisito);
        vinculo.persist();

        return new Uid(vinculo.getUid());
    }
}
