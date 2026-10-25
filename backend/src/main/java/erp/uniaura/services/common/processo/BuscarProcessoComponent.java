package erp.uniaura.services.common.processo;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Processo;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarProcessoComponent extends BaseComponent {

    // --- BUSCA PROCESSO POR UID OU LANÇA EXCEÇÃO ---
    public Processo buscarPorUid(Long uid) {
        return findByUid(Processo.class, uid)
                .orElseThrow(() -> new BusinessException("Processo não encontrado."));
    }

    // --- BUSCA PROCESSO PELO PROTOCOLO OU LANÇA EXCEÇÃO ---
    public Processo buscarPorProtocolo(String protocolo) {
        List<Processo> encontrados = em()
                .createQuery("select p from Processo p where p.protocolo = :protocolo", Processo.class)
                .setParameter("protocolo", protocolo)
                .getResultList();

        if (encontrados.isEmpty()) {
            throw new BusinessException("Processo não encontrado.");
        }

        return encontrados.get(0);
    }
}