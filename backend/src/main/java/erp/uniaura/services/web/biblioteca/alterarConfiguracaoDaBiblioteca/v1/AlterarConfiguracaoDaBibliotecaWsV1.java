package erp.uniaura.services.web.biblioteca.alterarConfiguracaoDaBiblioteca.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Ws
@RequiredArgsConstructor
public class AlterarConfiguracaoDaBibliotecaWsV1 extends BaseWs {

    private final AlterarConfiguracaoDaBibliotecaServiceV1 alterarConfiguracaoDaBibliotecaServiceV1;

    // --- ENDPOINT QUE ALTERA OS PARÂMETROS DA BIBLIOTECA ---
    @PostMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_BIBLIOTECARIO"})
    @Operation(summary = "Altera os parâmetros da biblioteca",
            description = "Grava os prazos de empréstimo, os limites e o valor da multa por dia; vale para os empréstimos feitos a partir dali. Erro 422 se a configuração não tiver sido inicializada.")
    public void alterar(@Valid @RequestBody AlterarConfiguracaoDaBibliotecaParamsV1 params) {
        alterarConfiguracaoDaBibliotecaServiceV1.alterar(params);
    }
}
