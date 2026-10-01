package erp.uniaura.modules.professor.services.web.rest.v1;

import erp.uniaura.modules.professor.dto.ProfessorDisciplinaResponseDTO;
import erp.uniaura.modules.professor.dto.ProfessorRequestDTO;
import erp.uniaura.modules.professor.dto.ProfessorResponseDTO;
import erp.uniaura.modules.professor.dto.VincularDisciplinaRequestDTO;
import erp.uniaura.modules.professor.service.ProfessorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/professores")
@RequiredArgsConstructor
@Tag(name = "Professores", description = "CRUD de professores e vínculos com disciplinas")
public class ProfessorWsV1 {

    private final ProfessorService professorService;

    // --- LISTA PROFESSORES PAGINADOS ---
    @GetMapping
    @Operation(summary = "Lista professores paginados")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<ProfessorResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(professorService.listarProfessor(pageable));
    }

    // --- BUSCA PROFESSOR PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca professor pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.buscarProfessorPorId(id));
    }

    // --- CRIA UM PROFESSOR ---
    @PostMapping
    @Operation(summary = "Cria um professor (cria também o usuário associado com role PROFESSOR)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ProfessorResponseDTO> criar(@Valid @RequestBody ProfessorRequestDTO dto, UriComponentsBuilder uriBuilder) {
        ProfessorResponseDTO criadoProfessorDTO = professorService.criar(dto);
        URI uri = uriBuilder.path("/professores/{id}").buildAndExpand(criadoProfessorDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(criadoProfessorDTO);
    }

    // --- ATUALIZA UM PROFESSOR EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um professor existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProfessorRequestDTO dto) {
        return ResponseEntity.ok(professorService.atualizar(id, dto));
    }

    // --- REMOVE UM PROFESSOR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um professor")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        professorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA AS DISCIPLINAS LECIONADAS PELO PROFESSOR ---
    @GetMapping("/{id}/disciplinas")
    @Operation(summary = "Lista as disciplinas lecionadas pelo professor")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<List<ProfessorDisciplinaResponseDTO>> listarDisciplinas(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.listarDisciplinas(id));
    }

    // --- VINCULA UMA DISCIPLINA AO PROFESSOR ---
    @PostMapping("/{id}/disciplinas")
    @Operation(summary = "Vincula uma disciplina ao professor")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<ProfessorDisciplinaResponseDTO> vincularDisciplina(@PathVariable Long id,
                                                                             @Valid @RequestBody VincularDisciplinaRequestDTO dto) {
        return ResponseEntity.status(201).body(professorService.vincularDisciplina(id, dto));
    }

    // --- REMOVE O VÍNCULO ENTRE PROFESSOR E DISCIPLINA ---
    @DeleteMapping("/{id}/disciplinas/{disciplinaId}")
    @Operation(summary = "Remove o vínculo entre professor e disciplina")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<Void> desvincularDisciplina(@PathVariable Long id, @PathVariable Long disciplinaId) {
        professorService.desvincularDisciplina(id, disciplinaId);
        return ResponseEntity.noContent().build();
    }
}
