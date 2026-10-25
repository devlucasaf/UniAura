package erp.uniaura.services.common.material;

import cloudsupport.services.BaseComponent;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Material;

import org.springframework.stereotype.Component;

@Component
public class BuscarMaterialComponent extends BaseComponent {

    // --- BUSCA MATERIAL POR UID OU LANÇA EXCEÇÃO ---
    public Material buscarPorUid(Long uid) {
        return findByUid(Material.class, uid)
                .orElseThrow(() -> new BusinessException("Material não encontrado."));
    }
}
