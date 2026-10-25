package erp.uniaura.services.web.biblioteca.removerExemplar.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.persistence.entities.StatusExemplar;
import erp.uniaura.services.common.biblioteca.BuscarExemplarComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoverExemplarServiceV1 extends BaseService {

    private final BuscarExemplarComponent buscarExemplarComponent;

    // --- REMOVE O EXEMPLAR SE NÃO ESTIVER EMPRESTADO NEM RESERVADO ---
    @Transactional
    public void remover(RemoverExemplarParamsV1 params) {
        Exemplar exemplar = buscarExemplarComponent.buscarPorUid(params.getUid());

        if (exemplar.getStatus() == StatusExemplar.EMPRESTADO || exemplar.getStatus() == StatusExemplar.RESERVADO) {
            throw new BusinessException("Não é possível excluir exemplar emprestado ou reservado.");
        }

        exemplar.remove();
    }
}
