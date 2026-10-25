package erp.uniaura.services.web.relatorio.gerarRelatorioFinanceiro.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.BaseWs;
import cloudsupport.services.web.Ws;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@Ws
@RequiredArgsConstructor
public class GerarRelatorioFinanceiroWsV1 extends BaseWs {

    private final GerarRelatorioFinanceiroServiceV1 gerarRelatorioFinanceiroServiceV1;

    // --- SÓ O ADMIN E A EQUIPE FINANCEIRA VEEM OS INDICADORES FINANCEIROS ---
    @GetMapping
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    @Operation(summary = "Gera o relatório financeiro",
            description = "Consolida quantidades e valores das mensalidades por status e a taxa de inadimplência.")
    public GerarRelatorioFinanceiroRetornoV1 gerar() {
        return gerarRelatorioFinanceiroServiceV1.gerar();
    }
}
