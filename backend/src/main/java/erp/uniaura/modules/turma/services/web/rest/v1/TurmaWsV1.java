package erp.uniaura.modules.turma.services.web.rest.v1;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;

import erp.uniaura.modules.aluno.dto.AlunoResponseDTO;
import erp.uniaura.modules.turma.dto.TurmaDisciplinaResponseDTO;
import erp.uniaura.modules.turma.dto.TurmaRequestDTO;
import erp.uniaura.modules.turma.dto.TurmaResponseDTO;
import erp.uniaura.modules.turma.dto.VincularDisciplinaTurmaRequestDTO;
import erp.uniaura.modules.turma.service.TurmaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "CRUD de turmas, vínculos com disciplinas e listagem de alunos")
public class TurmaWsV1 {

    private final TurmaService turmaService;

    // --- LISTA TURMAS ---
    @GetMapping
    @Operation(summary = "Lista turmas paginadas, opcionalmente filtrando por curso e/ou período letivo")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<TurmaResponseDTO>> listar(@RequestParam(required = false) Long cursoId,
            @RequestParam(required = false) String periodoLetivo, Pageable pageable) {
        return ResponseEntity.ok(turmaService.listar(cursoId, periodoLetivo, pageable));
    }

    // --- BUSCA TURMA PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca turma pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<TurmaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.buscarPorId(id));
    }

    // --- BUSCA TURMA PELO CÓDIGO ÚNICO ---
    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Busca turma pelo código único")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<TurmaResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(turmaService.buscarPorCodigo(codigo));
    }

    // --- CRIA UMA NOVA TURMA ---
    @PostMapping
    @Operation(summary = "Cria uma nova turma")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<TurmaResponseDTO> criar(@Valid
              @RequestBody TurmaRequestDTO dto, UriComponentsBuilder uriBuilder) {
        TurmaResponseDTO turmaCriadaDTO = turmaService.criar(dto);
        URI uri = uriBuilder.path("/turmas/{id}").buildAndExpand(turmaCriadaDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(turmaCriadaDTO);
    }

    // --- ATUALIZA UMA TURMA EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma turma existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<TurmaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequestDTO dto) {
        return ResponseEntity.ok(turmaService.atualizar(id, dto));
    }

    // --- REMOVE UMA TURMA ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma turma")
    @HasAuthority("ROLE_ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        turmaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    // --- LISTA OS ALUNOS MATRICULADOS NA TURMA ---
    @GetMapping("/{id}/alunos")
    @Operation(summary = "Lista os alunos matriculados na turma")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<AlunoResponseDTO>> listarAlunos(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(turmaService.listarAlunos(id, pageable));
    }

    // --- LISTA AS DISCIPLINAS DA TURMA ---
    @GetMapping("/{id}/disciplinas")
    @Operation(summary = "Lista as disciplinas vinculadas à turma")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<List<TurmaDisciplinaResponseDTO>> listarDisciplinas(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.listarDisciplinas(id));
    }

    // --- ADICIONA UMA DISCIPLINA À TURMA COM PROFESSOR E HORÁRIO ---
    @PostMapping("/{id}/disciplinas")
    @Operation(summary = "Adiciona uma disciplina à turma (com professor responsável e horário)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<TurmaDisciplinaResponseDTO> vincularDisciplina(@PathVariable Long id,
            @Valid @RequestBody VincularDisciplinaTurmaRequestDTO dto) {
        return ResponseEntity.status(201).body(turmaService.vincularDisciplina(id, dto));
    }

    // --- REMOVE O VÍNCULO DE UMA DISCIPLINA NA TURMA ---
    @DeleteMapping("/{id}/disciplinas/{disciplinaId}")
    @Operation(summary = "Remove o vínculo de uma disciplina na turma")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<Void> desvincularDisciplina(@PathVariable Long id, @PathVariable Long disciplinaId) {
        turmaService.desvincularDisciplina(id, disciplinaId);
        return ResponseEntity.noContent().build();
    }
}

