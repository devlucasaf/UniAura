package erp.uniaura.services.web.biblioteca.gerarCodigoDeBarras.v1;

import cloudsupport.services.BaseService;

import erp.uniaura.services.common.biblioteca.GerarCodigoDeBarrasComponent;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GerarCodigoDeBarrasServiceV1 extends BaseService {

    private final GerarCodigoDeBarrasComponent gerarCodigoDeBarrasComponent;

    // --- GERA UM NOVO CÓDIGO DE BARRAS PARA EXEMPLAR ---
    @Transactional(readOnly = true)
    public GerarCodigoDeBarrasRetornoV1 gerar() {
        GerarCodigoDeBarrasRetornoV1 retorno = new GerarCodigoDeBarrasRetornoV1();

        retorno.setCodigoBarras(gerarCodigoDeBarrasComponent.gerar());

        return retorno;
    }
}
