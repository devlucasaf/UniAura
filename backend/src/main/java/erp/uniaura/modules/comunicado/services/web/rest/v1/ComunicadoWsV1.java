package erp.uniaura.modules.comunicado.services.web.rest.v1;

import erp.uniaura.modules.comunicado.dto.ComunicadoRequestDTO;
import erp.uniaura.modules.comunicado.dto.ComunicadoResponseDTO;
import erp.uniaura.modules.comunicado.model.PublicoAlvoComunicado;
import erp.uniaura.modules.comunicado.service.ComunicadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.IsAuthenticated;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/comunicados")
@RequiredArgsConstructor
@Tag(name = "Comunicados", description = "Avisos da coordenação/professores para alunos e turmas")
public class ComunicadoWsV1 {

    private final ComunicadoService comunicadoService;

    // --- LISTA COMUNICADOS ---
    @GetMapping
    @Operation(summary = "Lista comunicados (opcionalmente filtrando por público-alvo)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<ComunicadoResponseDTO>> listar(@RequestParam(required = false)
            PublicoAlvoComunicado publicoAlvo, Pageable pageable) {
        return ResponseEntity.ok(comunicadoService.listar(publicoAlvo, pageable));
    }

    // --- FEED DE COMUNICADOS RELEVANTES PARA O USUÁRIO AUTENTICADO ---
    @GetMapping("/mural")
    @Operation(summary = "Feed de comunicados relevantes para o usuário autenticado")
    @IsAuthenticated
    public ResponseEntity<List<ComunicadoResponseDTO>> mural() {
        return ResponseEntity.ok(comunicadoService.mural());
    }

    // --- CRIA UM NOVO COMUNICADO ---
    @PostMapping
    @Operation(summary = "Cria um comunicado")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<ComunicadoResponseDTO> criar(@Valid @RequestBody ComunicadoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        ComunicadoResponseDTO comunicadoCriadoDTO = comunicadoService.criar(dto);
        URI uri = uriBuilder.path("/comunicados/{id}").buildAndExpand(comunicadoCriadoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(comunicadoCriadoDTO);
    }

    // --- ATUALIZA UM COMUNICADO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um comunicado (autor ou coordenação/admin)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<ComunicadoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ComunicadoRequestDTO dto) {
        return ResponseEntity.ok(comunicadoService.atualizar(id, dto));
    }

    // --- REMOVE UM COMUNICADO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um comunicado (autor ou coordenação/admin)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        comunicadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
