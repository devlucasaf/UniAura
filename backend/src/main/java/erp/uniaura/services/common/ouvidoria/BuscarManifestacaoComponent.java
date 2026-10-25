package erp.uniaura.services.common.ouvidoria;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Manifestacao;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarManifestacaoComponent extends BaseComponent {

    // --- BUSCA MANIFESTAÇÃO POR UID OU LANÇA EXCEÇÃO ---
    public Manifestacao buscarPorUid(Long uid) {
        return findByUid(Manifestacao.class, uid)
                .orElseThrow(() -> new BusinessException("Manifestação não encontrada."));
    }

    // --- BUSCA MANIFESTAÇÃO PELO PROTOCOLO OU LANÇA EXCEÇÃO ---
    public Manifestacao buscarPorProtocolo(String protocolo) {
        List<Manifestacao> encontradas = em()
                .createQuery("select m from Manifestacao m where m.protocolo = :protocolo", Manifestacao.class)
                .setParameter("protocolo", protocolo)
                .getResultList();

        if (encontradas.isEmpty()) {
            throw new BusinessException("Manifestação não encontrada.");
        }

        return encontradas.get(0);
    }
}
