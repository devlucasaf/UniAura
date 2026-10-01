package erp.uniaura.modules.lgpd.services.web.rest.v1;

import erp.uniaura.modules.lgpd.dto.DadosPessoaisResponseDTO;
import erp.uniaura.modules.lgpd.service.LgpdService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Ws("/lgpd")
@RequiredArgsConstructor
@Tag(name = "LGPD", description = "Direitos do titular dos dados (Lei Geral de Proteção de Dados)")
public class LgpdWsV1 {

    private final LgpdService lgpdService;

    // --- EXPORTA TODOS OS DADOS PESSOAIS DO USUÁRIO AUTENTICADO ---
    @GetMapping("/meus-dados")
    @Operation(summary = "Exporta todos os dados pessoais do usuário autenticado (direito de acesso da LGPD)")
    @IsAuthenticated
    public ResponseEntity<DadosPessoaisResponseDTO> meusDados() {
        return ResponseEntity.ok(lgpdService.meusDados());
    }
}
