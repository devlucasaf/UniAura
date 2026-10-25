package erp.uniaura.services.common.turma;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.Turma;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConsultarUidDasTurmasComponent extends BaseComponent {

    // --- PARA QUEM GUARDA O id DA TURMA E PRECISA DEVOLVER O uid: UMA CONSULTA PARA TODOS OS IDS ---
    public Map<Long, Long> uidsPorId(Collection<Long> ids) {
        Map<Long, Long> uids = new HashMap<>();

        Collection<Long> idsValidos = ids.stream().filter(id -> id != null).distinct().toList();
        if (idsValidos.isEmpty()) {
            return uids;
        }

        em().createQuery("select t from Turma t where t.id in :ids", Turma.class)
                .setParameter("ids", idsValidos)
                .getResultList()
                .forEach(turma -> uids.put(turma.getId(), turma.getUid()));

        return uids;
    }
}
