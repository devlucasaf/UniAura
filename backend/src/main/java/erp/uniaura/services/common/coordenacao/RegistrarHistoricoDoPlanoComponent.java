package erp.uniaura.services.common.coordenacao;

import cloudsupport.services.BaseComponent;

import erp.uniaura.persistence.entities.AvaliacaoPlanoEnsino;
import erp.uniaura.persistence.entities.PlanoEnsino;
import erp.uniaura.persistence.entities.StatusPlanoEnsino;
import erp.uniaura.persistence.entities.Usuario;

import org.springframework.stereotype.Component;

@Component
public class RegistrarHistoricoDoPlanoComponent extends BaseComponent {

    // --- GRAVA UM PASSO NO HISTÓRICO DO PLANO A CADA MUDANÇA DE STATUS ---
    public void registrar(PlanoEnsino plano, Usuario autor, StatusPlanoEnsino anterior, StatusPlanoEnsino novo, String parecer) {
        AvaliacaoPlanoEnsino historico = new AvaliacaoPlanoEnsino();

        historico.setPlanoEnsino(plano);
        historico.setAutor(autor);
        historico.setStatusAnterior(anterior);
        historico.setStatusNovo(novo);
        historico.setParecer(parecer);
        historico.persist();
    }
}
