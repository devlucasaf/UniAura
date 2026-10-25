package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.TurmaDisciplina;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscarPlanoDeEnsinoComponent extends BaseComponent {

    // --- PELO UID, QUE É O IDENTIFICADOR PÚBLICO DO PLANO ---
    public PlanoEnsino buscarPorUid(Long uid) {
        return findByUid(PlanoEnsino.class, uid)
                .orElseThrow(() -> new BusinessException("Plano de ensino não encontrado."));
    }

    // --- CADA TURMA E DISCIPLINA TEM NO MÁXIMO UM PLANO ---
    public Optional<PlanoEnsino> buscarPorTurmaDisciplina(TurmaDisciplina turmaDisciplina) {
        List<PlanoEnsino> encontrados = em()
                .createQuery("select p from PlanoEnsino p where p.turmaDisciplina = :turmaDisciplina", PlanoEnsino.class)
                .setParameter("turmaDisciplina", turmaDisciplina)
                .getResultList();

        if (encontrados.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(encontrados.get(0));
    }
}
