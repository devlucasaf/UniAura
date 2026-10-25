package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.ParticipanteReuniao;
import erp.uniaura.persistence.entities.ReuniaoColegiado;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarParticipantesDaReuniaoComponent extends BaseComponent {

    // --- OS CONVOCADOS, NA ORDEM EM QUE FORAM CONVOCADOS ---
    public List<ParticipanteReuniao> buscar(ReuniaoColegiado reuniao) {
        return em()
                .createQuery("select p from ParticipanteReuniao p where p.reuniao = :reuniao order by p.criadoEm asc", ParticipanteReuniao.class)
                .setParameter("reuniao", reuniao)
                .getResultList();
    }
}
