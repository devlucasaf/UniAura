package erp.uniaura.modules.autenticacao.services.web.rest.v1;

import erp.uniaura.modules.autenticacao.service.AutenticacaoService;
import erp.uniaura.modules.usuario.dto.UsuarioResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Ws("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Dados do usuário autenticado (login/registro são feitos via Keycloak)")
public class AutenticacaoWsV1 {

    private final AutenticacaoService autenticacaoService;

    // --- RETORNA OS DADOS DO USUÁRIO AUTENTICADO ---
    @GetMapping("/me")
    @IsAuthenticated
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Retorna os dados do usuário atualmente autenticado")
    public ResponseEntity<UsuarioResponseDTO> me() {
        return ResponseEntity.ok(autenticacaoService.dadosDoUsuario());
    }
}
