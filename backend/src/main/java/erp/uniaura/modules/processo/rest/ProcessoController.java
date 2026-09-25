package erp.uniaura.modules.processo.rest;

import erp.uniaura.modules.processo.dto.CancelarProcessoRequestDTO;
import erp.uniaura.modules.processo.dto.ProcessoRequestDTO;
import erp.uniaura.modules.processo.dto.ProcessoResponseDTO;
import erp.uniaura.modules.processo.dto.TramitarProcessoRequestDTO;
import erp.uniaura.modules.processo.model.StatusProcesso;
import erp.uniaura.modules.processo.model.TipoProcesso;
import erp.uniaura.modules.processo.service.ProcessoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/processos")
@RequiredArgsConstructor
@Tag(name = "Processos", description = "Requerimentos acadêmicos abertos pelo aluno e tramitados pela secretaria")
public class ProcessoController {

    private final ProcessoService processoService;

    // --- O ALUNO ABRE UM REQUERIMENTO ---
    @PostMapping
    @Operation(summary = "Abre um novo processo em nome do aluno autenticado")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<ProcessoResponseDTO> abrir(@Valid @RequestBody ProcessoRequestDTO dto,
                                                     UriComponentsBuilder uriBuilder) {
        ProcessoResponseDTO criado = processoService.abrir(dto);
        URI uri = uriBuilder.path("/processos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- O ALUNO CONSULTA OS PRÓPRIOS REQUERIMENTOS ---
    @GetMapping("/meus")
    @Operation(summary = "Lista os processos do aluno autenticado")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Page<ProcessoResponseDTO>> listarMeusProcessos(
            @RequestParam(value = "status", required = false) StatusProcesso status,
            Pageable pageable) {
        return ResponseEntity.ok(processoService.listarMeusProcessos(status, pageable));
    }

    // --- A SECRETARIA CONSULTA A FILA DE REQUERIMENTOS ---
    @GetMapping
    @Operation(summary = "Lista processos com filtros opcionais de status e tipo")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<Page<ProcessoResponseDTO>> listar(
            @RequestParam(value = "status", required = false) StatusProcesso status,
            @RequestParam(value = "tipo", required = false) TipoProcesso tipo,
            Pageable pageable) {
        return ResponseEntity.ok(processoService.listar(status, tipo, pageable));
    }

    // --- DETALHE COM A LINHA DO TEMPO ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca um processo pelo ID (o aluno só enxerga os próprios)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','ALUNO')")
    public ResponseEntity<ProcessoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(processoService.buscarPorId(id));
    }

    // --- CONSULTA PELO NÚMERO DE PROTOCOLO ---
    @GetMapping("/protocolo/{protocolo}")
    @Operation(summary = "Busca um processo pelo número de protocolo")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','ALUNO')")
    public ResponseEntity<ProcessoResponseDTO> buscarPorProtocolo(@PathVariable String protocolo) {
        return ResponseEntity.ok(processoService.buscarPorProtocolo(protocolo));
    }

    // --- A SECRETARIA MOVIMENTA O PROCESSO ---
    @PutMapping("/{id}/tramitar")
    @Operation(summary = "Movimenta o processo para um novo status, registrando o despacho")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<ProcessoResponseDTO> tramitar(@PathVariable UUID id,
                                                        @Valid @RequestBody TramitarProcessoRequestDTO dto) {
        return ResponseEntity.ok(processoService.tramitar(id, dto));
    }

    // --- O ALUNO DESISTE DO REQUERIMENTO ---
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancela um processo ainda não concluído (apenas o aluno autor)")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<ProcessoResponseDTO> cancelar(
            @PathVariable UUID id,
            @RequestBody(required = false) CancelarProcessoRequestDTO dto) {
        return ResponseEntity.ok(processoService.cancelar(id, dto == null ? null : dto.getMotivo()));
    }
}
