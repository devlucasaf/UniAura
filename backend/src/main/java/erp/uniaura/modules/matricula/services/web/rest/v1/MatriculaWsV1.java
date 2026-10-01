package erp.uniaura.modules.matricula.services.web.rest.v1;

import erp.uniaura.modules.matricula.dto.AlterarStatusMatriculaRequestDTO;
import erp.uniaura.modules.matricula.dto.MatriculaRequestDTO;
import erp.uniaura.modules.matricula.dto.MatriculaResponseDTO;
import erp.uniaura.modules.matricula.service.MatriculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/matriculas")
@RequiredArgsConstructor
@Tag(name = "Matrículas", description = "Matrícula de alunos em turmas e mudança de status (trancar/cancelar)")
public class MatriculaWsV1 {

    private final MatriculaService matriculaService;

    // --- LISTA MATRÍCULAS ---
    @GetMapping
    @Operation(summary = "Lista matrículas (filtro opcional ?aluno={id} retorna o histórico do aluno)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Page<MatriculaResponseDTO>> listar(@RequestParam(value = "aluno", required = false) Long alunoId,
            Pageable pageable) {
        return ResponseEntity.ok(matriculaService.listar(alunoId, pageable));
    }

    // --- BUSCA MATRÍCULA POR ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca matrícula pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    // --- MATRICULA UM ALUNO EM UMA TURMA ---
    @PostMapping
    @Operation(summary = "Matricula um aluno em uma turma")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<MatriculaResponseDTO> matricular(@Valid @RequestBody MatriculaRequestDTO dto, UriComponentsBuilder uriBuilder) {
        MatriculaResponseDTO criada = matriculaService.matricular(dto);
        URI uri = uriBuilder.path("/matriculas/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- TRANCA UMA MATRÍCULA ATIVA ---
    @PutMapping("/{id}/trancar")
    @Operation(summary = "Tranca a matrícula (libera vaga e remove vínculo de turma atual do aluno)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<MatriculaResponseDTO> trancar(@PathVariable Long id,
            @RequestBody(required = false) AlterarStatusMatriculaRequestDTO dto) {
        return ResponseEntity.ok(matriculaService.trancar(id, dto));
    }

    // --- CANCELA UMA MATRÍCULA ATIVA ---
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancela a matrícula (libera vaga e remove vínculo de turma atual do aluno)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<MatriculaResponseDTO> cancelar(@PathVariable Long id,
            @RequestBody(required = false) AlterarStatusMatriculaRequestDTO dto) {
        return ResponseEntity.ok(matriculaService.cancelar(id, dto));
    }
}

