package erp.uniaura.modules.calendario.rest;

import erp.uniaura.modules.calendario.dto.EventoAcademicoRequestDTO;
import erp.uniaura.modules.calendario.dto.EventoAcademicoResponseDTO;
import erp.uniaura.modules.calendario.service.EventoAcademicoService;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/eventos-academicos")
@RequiredArgsConstructor
@Tag(name = "Calendário Acadêmico", description = "Provas, feriados, matrícula, recessos e eventos institucionais")
public class EventoAcademicoController {

    private final EventoAcademicoService eventoAcademicoService;

    // --- LISTA TODOS OS EVENTOS ---
    @GetMapping
    @Operation(summary = "Lista todos os eventos do calendário acadêmico")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<Page<EventoAcademicoResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(eventoAcademicoService.listar(pageable));
    }

    // --- LISTA OS EVENTOS PÚBLICOS ---
    @GetMapping("/publicos")
    @Operation(summary = "Lista os eventos marcados como públicos (endpoint sem autenticação)")
    public ResponseEntity<List<EventoAcademicoResponseDTO>> listarPublicos() {
        return ResponseEntity.ok(eventoAcademicoService.listarPublicos());
    }

    // --- BUSCA EVENTO PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca um evento pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<EventoAcademicoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(eventoAcademicoService.buscarPorId(id));
    }

    // --- CRIA UM NOVO EVENTO ---
    @PostMapping
    @Operation(summary = "Cria um evento no calendário acadêmico")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<EventoAcademicoResponseDTO> criar(@Valid @RequestBody EventoAcademicoRequestDTO dto,
                                                              UriComponentsBuilder uriBuilder) {
        EventoAcademicoResponseDTO criado = eventoAcademicoService.criar(dto);
        URI uri = uriBuilder.path("/eventos-academicos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM EVENTO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um evento do calendário acadêmico")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<EventoAcademicoResponseDTO> atualizar(@PathVariable UUID id,
                                                                  @Valid @RequestBody EventoAcademicoRequestDTO dto) {
        return ResponseEntity.ok(eventoAcademicoService.atualizar(id, dto));
    }

    // --- REMOVE UM EVENTO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um evento do calendário acadêmico")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        eventoAcademicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
