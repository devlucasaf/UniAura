package erp.uniaura.modules.calendario.services.web.rest.v1;

import erp.uniaura.modules.calendario.dto.EventoAcademicoRequestDTO;
import erp.uniaura.modules.calendario.dto.EventoAcademicoResponseDTO;
import erp.uniaura.modules.calendario.service.EventoAcademicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/eventos-academicos")
@RequiredArgsConstructor
@Tag(name = "Calendário Acadêmico", description = "Provas, feriados, matrícula, recessos e eventos institucionais")
public class EventoAcademicoWsV1 {

    private final EventoAcademicoService eventoAcademicoService;

    // --- LISTA TODOS OS EVENTOS ---
    @GetMapping
    @Operation(summary = "Lista todos os eventos do calendário acadêmico")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
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
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<EventoAcademicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoAcademicoService.buscarPorId(id));
    }

    // --- CRIA UM NOVO EVENTO ---
    @PostMapping
    @Operation(summary = "Cria um evento no calendário acadêmico")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<EventoAcademicoResponseDTO> criar(@Valid @RequestBody EventoAcademicoRequestDTO dto,
            UriComponentsBuilder uriBuilder) {
        EventoAcademicoResponseDTO criado = eventoAcademicoService.criar(dto);
        URI uri = uriBuilder.path("/eventos-academicos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM EVENTO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um evento do calendário acadêmico")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<EventoAcademicoResponseDTO> atualizar(@PathVariable Long id,
            @Valid @RequestBody EventoAcademicoRequestDTO dto) {
        return ResponseEntity.ok(eventoAcademicoService.atualizar(id, dto));
    }

    // --- REMOVE UM EVENTO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um evento do calendário acadêmico")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoAcademicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
