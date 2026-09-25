package erp.uniaura.modules.ouvidoria.rest;

import erp.uniaura.modules.ouvidoria.dto.ManifestacaoRequestDTO;
import erp.uniaura.modules.ouvidoria.dto.ManifestacaoResponseDTO;
import erp.uniaura.modules.ouvidoria.dto.ResponderManifestacaoRequestDTO;
import erp.uniaura.modules.ouvidoria.model.StatusManifestacao;
import erp.uniaura.modules.ouvidoria.model.TipoManifestacao;
import erp.uniaura.modules.ouvidoria.service.OuvidoriaService;

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
@RequestMapping("/ouvidoria/manifestacoes")
@RequiredArgsConstructor
@Tag(name = "Ouvidoria", description = "Reclamações, denúncias, sugestões e elogios registrados pela comunidade acadêmica")
public class OuvidoriaController {

    private final OuvidoriaService ouvidoriaService;

    // --- QUALQUER USUÁRIO AUTENTICADO PODE SE MANIFESTAR ---
    @PostMapping
    @Operation(summary = "Registra uma manifestação em nome do usuário autenticado")
    public ResponseEntity<ManifestacaoResponseDTO> registrar(@Valid @RequestBody ManifestacaoRequestDTO dto,
                                                             UriComponentsBuilder uriBuilder) {
        ManifestacaoResponseDTO criada = ouvidoriaService.registrar(dto);
        URI uri = uriBuilder.path("/ouvidoria/manifestacoes/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- O AUTOR ACOMPANHA AS PRÓPRIAS MANIFESTAÇÕES ---
    @GetMapping("/minhas")
    @Operation(summary = "Lista as manifestações do usuário autenticado")
    public ResponseEntity<Page<ManifestacaoResponseDTO>> listarMinhas(
            @RequestParam(value = "status", required = false) StatusManifestacao status,
            Pageable pageable) {
        return ResponseEntity.ok(ouvidoriaService.listarMinhas(status, pageable));
    }

    // --- A OUVIDORIA CONSULTA A FILA DE MANIFESTAÇÕES ---
    @GetMapping
    @Operation(summary = "Lista manifestações com filtros opcionais de status e tipo")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<Page<ManifestacaoResponseDTO>> listar(
            @RequestParam(value = "status", required = false) StatusManifestacao status,
            @RequestParam(value = "tipo", required = false) TipoManifestacao tipo,
            Pageable pageable) {
        return ResponseEntity.ok(ouvidoriaService.listar(status, tipo, pageable));
    }

    // --- DETALHE COM O HISTÓRICO DE RESPOSTAS ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca uma manifestação pelo ID (o autor só enxerga as próprias)")
    public ResponseEntity<ManifestacaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(ouvidoriaService.buscarPorId(id));
    }

    // --- CONSULTA PELO NÚMERO DE PROTOCOLO ---
    @GetMapping("/protocolo/{protocolo}")
    @Operation(summary = "Busca uma manifestação pelo número de protocolo")
    public ResponseEntity<ManifestacaoResponseDTO> buscarPorProtocolo(@PathVariable String protocolo) {
        return ResponseEntity.ok(ouvidoriaService.buscarPorProtocolo(protocolo));
    }

    // --- A OUVIDORIA RESPONDE E MOVIMENTA A MANIFESTAÇÃO ---
    @PutMapping("/{id}/responder")
    @Operation(summary = "Registra uma resposta e move a manifestação para um novo status")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<ManifestacaoResponseDTO> responder(@PathVariable UUID id,
                                                             @Valid @RequestBody ResponderManifestacaoRequestDTO dto) {
        return ResponseEntity.ok(ouvidoriaService.responder(id, dto));
    }
}
