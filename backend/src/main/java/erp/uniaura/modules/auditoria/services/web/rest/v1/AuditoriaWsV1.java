package erp.uniaura.modules.auditoria.services.web.rest.v1;

import erp.uniaura.modules.auditoria.dto.LogAuditoriaResponseDTO;
import erp.uniaura.modules.auditoria.service.AuditoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Ws("/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Trilha de ações sensíveis realizadas no sistema")
public class AuditoriaWsV1 {

    private final AuditoriaService auditoriaService;

    // --- LISTA OS LOGS DE AUDITORIA, COM FILTRO OPCIONAL POR ENTIDADE OU USUÁRIO ---
    @GetMapping
    @Operation(summary = "Lista os logs de auditoria (filtro opcional por entidade ou usuário)")
    @HasAuthority("ROLE_ADMIN")
    public ResponseEntity<Page<LogAuditoriaResponseDTO>> listar(@RequestParam(required = false) String entidade,
            @RequestParam(required = false) Long usuario, Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.listar(entidade, usuario, pageable));
    }
}
