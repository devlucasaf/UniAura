package erp.uniaura.services.web.biblioteca.detalharExemplar.v1;

import cloudsupport.services.BaseService;
import cloudsupport.services.BusinessException;

import erp.uniaura.persistence.entities.Exemplar;
import erp.uniaura.services.common.biblioteca.BuscarExemplarComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DetalharExemplarServiceV1 extends BaseService {

    private final BuscarExemplarComponent buscarExemplarComponent;

    // --- DETALHA UM EXEMPLAR PELO UID OU PELO CÓDIGO DE BARRAS ---
    @Transactional(readOnly = true)
    public DetalharExemplarRetornoV1 detalhar(DetalharExemplarParamsV1 params) {
        Exemplar exemplar = buscarExemplar(params);

        DetalharExemplarRetornoV1 retorno = new DetalharExemplarRetornoV1();

        retorno.setUid(exemplar.getUid());
        retorno.setLivroUid(exemplar.getLivro().getUid());
        retorno.setLivroTitulo(exemplar.getLivro().getTitulo());
        retorno.setCodigoBarras(exemplar.getCodigoBarras());
        retorno.setLocalizacao(exemplar.getLocalizacao());
        retorno.setStatus(exemplar.getStatus());
        retorno.setCriadoEm(exemplar.getCriadoEm());

        return retorno;
    }

    // --- BUSCA O EXEMPLAR PELO UID OU, SEM ELE, PELO CÓDIGO DE BARRAS ---
    private Exemplar buscarExemplar(DetalharExemplarParamsV1 params) {
        if (params.getUid() != null) {
            return buscarExemplarComponent.buscarPorUid(params.getUid());
        }

        if (params.getCodigoBarras() != null && !params.getCodigoBarras().isBlank()) {
            return buscarExemplarComponent.buscarPorCodigoDeBarras(params.getCodigoBarras());
        }

        throw new BusinessException("Informe o uid ou o código de barras do exemplar.");
    }
}
