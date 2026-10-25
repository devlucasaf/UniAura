package erp.uniaura.services.web.biblioteca.gerarCodigoDeBarras.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class GerarCodigoDeBarrasWsV1 extends BaseWs {

    private final GerarCodigoDeBarrasServiceV1 gerarCodigoDeBarrasServiceV1;

    // --- ENDPOINT QUE GERA UM CÓDIGO DE BARRAS ÚNICO PARA EXEMPLAR ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Gera um código de barras único para um novo exemplar",
            description = "Devolve uma sugestão de código de barras que ainda não está em uso. Nada é gravado.")
    public GerarCodigoDeBarrasRetornoV1 gerar() {
        return gerarCodigoDeBarrasServiceV1.gerar();
    }
}
