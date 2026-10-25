package erp.uniaura.services.web.relatorio.gerarRelatorioDaBiblioteca.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class GerarRelatorioDaBibliotecaWsV1 extends BaseWs {

    private final GerarRelatorioDaBibliotecaServiceV1 gerarRelatorioDaBibliotecaServiceV1;

    // --- SÓ O ADMIN E A BIBLIOTECA VEEM OS INDICADORES DO ACERVO ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_BIBLIOTECARIO"})
    @Operation(summary = "Gera o relatório da biblioteca",
            description = "Consolida exemplares por situação, a taxa de utilização do acervo e as multas pendentes.")
    public GerarRelatorioDaBibliotecaRetornoV1 gerar() {
        return gerarRelatorioDaBibliotecaServiceV1.gerar();
    }
}
