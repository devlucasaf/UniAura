package erp.uniaura.modules.comunicado.rest;

import erp.uniaura.modules.comunicado.dto.ComunicadoRequestDTO;
import erp.uniaura.modules.comunicado.dto.ComunicadoResponseDTO;
import erp.uniaura.modules.comunicado.model.PublicoAlvoComunicado;
import erp.uniaura.modules.comunicado.service.ComunicadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comunicados")
@RequiredArgsConstructor
@Tag(name = "Comunicados", description = "Avisos da coordenação/professores para alunos e turmas")
public class ComunicadoController {

    private final ComunicadoService comunicadoService;

    // --- LISTA COMUNICADOS (VISÃO ADMINISTRATIVA) ---
    @GetMapping
    @Operation(summary = "Lista comunicados (opcionalmente filtrando por público-alvo)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<Page<ComunicadoResponseDTO>> listar(
            @RequestParam(required = false) PublicoAlvoComunicado publicoAlvo,
            Pageable pageable) {
        return ResponseEntity.ok(comunicadoService.listar(publicoAlvo, pageable));
    }

    // --- MURAL: FEED DE COMUNICADOS RELEVANTES PARA O USUÁRIO AUTENTICADO ---
    @GetMapping("/mural")
    @Operation(summary = "Feed de comunicados relevantes para o usuário autenticado")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ComunicadoResponseDTO>> mural() {
        return ResponseEntity.ok(comunicadoService.mural());
    }

    // --- CRIA UM NOVO COMUNICADO ---
    @PostMapping
    @Operation(summary = "Cria um comunicado")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<ComunicadoResponseDTO> criar(@Valid @RequestBody ComunicadoRequestDTO dto,
                                                         UriComponentsBuilder uriBuilder) {
        ComunicadoResponseDTO criado = comunicadoService.criar(dto);
        URI uri = uriBuilder.path("/comunicados/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM COMUNICADO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um comunicado (autor ou coordenação/admin)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<ComunicadoResponseDTO> atualizar(@PathVariable UUID id,
                                                             @Valid @RequestBody ComunicadoRequestDTO dto) {
        return ResponseEntity.ok(comunicadoService.atualizar(id, dto));
    }

    // --- REMOVE UM COMUNICADO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um comunicado (autor ou coordenação/admin)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        comunicadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
