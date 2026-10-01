package erp.uniaura.modules.biblioteca.multa.services.web.rest.v1;

import erp.uniaura.modules.biblioteca.multa.dto.MultaResponseDTO;
import erp.uniaura.modules.biblioteca.multa.model.StatusMulta;
import erp.uniaura.modules.biblioteca.multa.service.MultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Ws("/biblioteca/multas")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Multas", description = "Multas geradas por atrasos")
public class MultaWsV1 {

    private final MultaService multaService;

    // --- LISTA AS MULTAS DE FORMA PAGINADA DE ACORDO COM O STATUS INFORMADO ---
    @GetMapping
    @Operation(summary = "Lista multas por status")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_FINANCEIRO"})
    public ResponseEntity<Page<MultaResponseDTO>> listar(@RequestParam(defaultValue = "PENDENTE")
            StatusMulta status, Pageable pageable) {
        return ResponseEntity.ok(multaService.listarPorStatus(status, pageable));
    }

    // --- LISTA AS MULTAS PENDENTES DE UM USUÁRIO ---
    @GetMapping("/usuario/{usuarioId}/pendentes")
    @IsAuthenticated
    public ResponseEntity<List<MultaResponseDTO>> pendentesDoUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(multaService.pendentesDoUsuario(usuarioId));
    }

    // --- REGISTRA O PAGAMENTO DE UMA MULTA ---
    @PostMapping("/{id}/pagar")
    @Operation(summary = "Registra pagamento da multa")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_FINANCEIRO"})
    public ResponseEntity<MultaResponseDTO> pagar(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.pagar(id));
    }

    // --- CANCELA UMA MULTA PELO SEU IDENTIFICADOR ---
    @PostMapping("/{id}/cancelar")
    @HasAnyAuthority({"ROLE_ADMIN"})
    public ResponseEntity<MultaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(multaService.cancelar(id));
    }
}

