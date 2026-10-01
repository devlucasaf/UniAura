package erp.uniaura.modules.relatorio.services.web.rest.v1;

import erp.uniaura.modules.relatorio.dto.RelatorioBibliotecaResponseDTO;
import erp.uniaura.modules.relatorio.dto.RelatorioFinanceiroResponseDTO;
import erp.uniaura.modules.relatorio.service.RelatorioBibliotecaService;
import erp.uniaura.modules.relatorio.service.RelatorioFinanceiroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Ws("/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Indicadores consolidados (BI) de financeiro e biblioteca")
public class RelatorioWsV1 {

    private final RelatorioFinanceiroService relatorioFinanceiroService;
    private final RelatorioBibliotecaService relatorioBibliotecaService;

    // --- CONSOLIDA OS INDICADORES DE INADIMPLÊNCIA E ARRECADAÇÃO ---
    @GetMapping("/financeiro")
    @Operation(summary = "Consolida os indicadores financeiros (inadimplência, valores em aberto e recebidos)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    public ResponseEntity<RelatorioFinanceiroResponseDTO> financeiro() {
        return ResponseEntity.ok(relatorioFinanceiroService.gerar());
    }

    // --- CONSOLIDA OS INDICADORES DE UTILIZAÇÃO DO ACERVO E MULTAS ---
    @GetMapping("/biblioteca")
    @Operation(summary = "Consolida os indicadores de utilização do acervo e multas pendentes")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_BIBLIOTECARIO"})
    public ResponseEntity<RelatorioBibliotecaResponseDTO> biblioteca() {
        return ResponseEntity.ok(relatorioBibliotecaService.gerar());
    }
}
