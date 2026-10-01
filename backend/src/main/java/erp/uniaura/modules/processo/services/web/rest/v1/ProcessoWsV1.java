package erp.uniaura.modules.processo.services.web.rest.v1;

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
import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/processos")
@RequiredArgsConstructor
@Tag(name = "Processos", description = "Requerimentos acadêmicos abertos pelo aluno e tramitados pela secretaria")
public class ProcessoWsV1 {

    private final ProcessoService processoService;

    // --- O ALUNO ABRE UM REQUERIMENTO ---
    @PostMapping
    @Operation(summary = "Abre um novo processo em nome do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<ProcessoResponseDTO> abrir(@Valid @RequestBody ProcessoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        ProcessoResponseDTO processoCriadoDTO = processoService.abrir(dto);
        URI uri = uriBuilder.path("/processos/{id}").buildAndExpand(processoCriadoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(processoCriadoDTO);
    }

    // --- O ALUNO CONSULTA OS PRÓPRIOS REQUERIMENTOS ---
    @GetMapping("/meus")
    @Operation(summary = "Lista os processos do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<Page<ProcessoResponseDTO>> listarMeusProcessos(@RequestParam(
            value = "status", required = false) StatusProcesso status, Pageable pageable) {
        return ResponseEntity.ok(processoService.listarMeusProcessos(status, pageable));
    }

    // --- A SECRETARIA CONSULTA A FILA DE REQUERIMENTOS ---
    @GetMapping
    @Operation(summary = "Lista processos com filtros opcionais de status e tipo")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Page<ProcessoResponseDTO>> listar(@RequestParam(value = "status", required = false) StatusProcesso status,
            @RequestParam(value = "tipo", required = false) TipoProcesso tipo, Pageable pageable) {
        return ResponseEntity.ok(processoService.listar(status, tipo, pageable));
    }

    // --- DETALHE COM A LINHA DO TEMPO ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca um processo pelo ID (o aluno só enxerga os próprios)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    public ResponseEntity<ProcessoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(processoService.buscarPorId(id));
    }

    // --- CONSULTA PELO NÚMERO DE PROTOCOLO ---
    @GetMapping("/protocolo/{protocolo}")
    @Operation(summary = "Busca um processo pelo número de protocolo")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_ALUNO"})
    public ResponseEntity<ProcessoResponseDTO> buscarPorProtocolo(@PathVariable String protocolo) {
        return ResponseEntity.ok(processoService.buscarPorProtocolo(protocolo));
    }

    // --- A SECRETARIA MOVIMENTA O PROCESSO ---
    @PutMapping("/{id}/tramitar")
    @Operation(summary = "Movimenta o processo para um novo status, registrando o despacho")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ProcessoResponseDTO> tramitar(@PathVariable Long id, @Valid @RequestBody TramitarProcessoRequestDTO dto) {
        return ResponseEntity.ok(processoService.tramitar(id, dto));
    }

    // --- O ALUNO DESISTE DO REQUERIMENTO ---
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancela um processo ainda não concluído (apenas o aluno autor)")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<ProcessoResponseDTO> cancelar(@PathVariable Long id, @RequestBody(required = false) CancelarProcessoRequestDTO dto) {
        return ResponseEntity.ok(processoService.cancelar(id, dto == null ? null : dto.getMotivo()));
    }
}
