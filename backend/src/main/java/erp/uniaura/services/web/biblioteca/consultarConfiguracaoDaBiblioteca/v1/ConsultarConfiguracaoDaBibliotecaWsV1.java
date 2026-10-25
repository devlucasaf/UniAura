package erp.uniaura.services.web.biblioteca.consultarConfiguracaoDaBiblioteca.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class ConsultarConfiguracaoDaBibliotecaWsV1 extends BaseWs {

    private final ConsultarConfiguracaoDaBibliotecaServiceV1 consultarConfiguracaoDaBibliotecaServiceV1;

    // --- ENDPOINT QUE RETORNA OS PARÂMETROS DA BIBLIOTECA ---
    @GetMapping
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    @Operation(summary = "Consulta os parâmetros da biblioteca",
            description = "Devolve os prazos de empréstimo, o limite de empréstimos simultâneos, o limite de renovações e o valor da multa por dia. Erro 422 se a configuração não tiver sido inicializada.")
    public ConsultarConfiguracaoDaBibliotecaRetornoV1 consultar() {
        return consultarConfiguracaoDaBibliotecaServiceV1.consultar();
    }
}
