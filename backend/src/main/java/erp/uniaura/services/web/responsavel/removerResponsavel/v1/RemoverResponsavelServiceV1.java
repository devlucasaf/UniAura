package erp.uniaura.services.web.responsavel.removerResponsavel.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.persistence.entities.Responsavel;
import erp.uniaura.services.common.responsavel.BuscarResponsavelComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverResponsavelServiceV1 extends BaseService {

    private final BuscarResponsavelComponent buscarResponsavelComponent;

    // --- REMOVE O RESPONSÁVEL PELO UID ---
    @Transactional
    public void remover(RemoverResponsavelParamsV1 params) {
        Responsavel responsavel = buscarResponsavelComponent.buscarPorUid(params.getUid());
        responsavel.remove();
    }
}
