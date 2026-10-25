package erp.uniaura.services.web.biblioteca.alterarExemplar.v1;

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
public class AlterarExemplarServiceV1 extends BaseService {

    private final BuscarExemplarComponent buscarExemplarComponent;

    // --- ALTERA LOCALIZAÇÃO E STATUS DO EXEMPLAR ---
    @Transactional
    public void alterar(AlterarExemplarParamsV1 params) {
        Exemplar exemplar = buscarExemplarComponent.buscarPorUid(params.getUid());

        if (params.getStatus() == StatusExemplar.EMPRESTADO || params.getStatus() == StatusExemplar.RESERVADO) {
            throw new BusinessException("Status " + params.getStatus() + " só pode ser definido via empréstimo/reserva.");
        }

        exemplar.setLocalizacao(params.getLocalizacao());

        if (params.getStatus() != null) {
            exemplar.setStatus(params.getStatus());
        }

        exemplar.merge();
    }
}
