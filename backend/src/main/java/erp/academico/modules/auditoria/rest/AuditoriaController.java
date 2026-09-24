package erp.academico.modules.auditoria.rest;

import erp.academico.modules.auditoria.dto.LogAuditoriaResponseDTO;
import erp.academico.modules.auditoria.service.AuditoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/auditoria")
@RequiredArgsConstructor
@Tag(name = "Auditoria", description = "Trilha de ações sensíveis realizadas no sistema")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    // --- LISTA OS LOGS DE AUDITORIA, COM FILTRO OPCIONAL POR ENTIDADE OU USUÁRIO ---
    @GetMapping
    @Operation(summary = "Lista os logs de auditoria (filtro opcional por entidade ou usuário)")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<LogAuditoriaResponseDTO>> listar(@RequestParam(required = false) String entidade,
            @RequestParam(required = false) UUID usuario, Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.listar(entidade, usuario, pageable));
    }
}
