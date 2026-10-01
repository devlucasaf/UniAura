package erp.uniaura.modules.nota.services.web.rest.v1;

import erp.uniaura.modules.nota.dto.BoletimResponseDTO;
import erp.uniaura.modules.nota.dto.NotaRequestDTO;
import erp.uniaura.modules.nota.dto.NotaResponseDTO;
import erp.uniaura.modules.nota.service.NotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("")
@RequiredArgsConstructor
@Tag(name = "Notas", description = "Lançamento, consulta e boletim de notas dos alunos")
public class NotaWsV1 {

    private final NotaService notaService;

    // --- LANÇA UMA NOVA NOTA ---
    @PostMapping("/notas")
    @Operation(summary = "Lança uma nova nota (somente o professor responsável, coordenador ou admin)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<NotaResponseDTO> lancar(@Valid @RequestBody NotaRequestDTO dto,
                                                  UriComponentsBuilder uriBuilder) {
        NotaResponseDTO criada = notaService.lancar(dto);
        URI uri = uriBuilder.path("/notas/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- ATUALIZA UMA NOTA EXISTENTE ---
    @PutMapping("/notas/{id}")
    @Operation(summary = "Atualiza uma nota existente (mantém auditoria de quem alterou)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<NotaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody NotaRequestDTO dto) {
        return ResponseEntity.ok(notaService.atualizar(id, dto));
    }

    // --- REMOVE UMA NOTA ---
    @DeleteMapping("/notas/{id}")
    @Operation(summary = "Remove uma nota lançada")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        notaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- TODAS AS NOTAS DA TURMA EM UMA DISCIPLINA ---
    @GetMapping("/turmas/{turmaId}/disciplinas/{disciplinaId}/notas")
    @Operation(summary = "Lista todas as notas de uma turma em uma disciplina")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<List<NotaResponseDTO>> listarPorTurmaDisciplina(@PathVariable Long turmaId, @PathVariable Long disciplinaId) {
        return ResponseEntity.ok(notaService.listarPorTurmaDisciplina(turmaId, disciplinaId));
    }

    // --- BOLETIM COMPLETO ---
    @GetMapping("/alunos/{alunoId}/notas")
    @Operation(summary = "Lista todas as notas de um aluno (boletim completo)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<List<NotaResponseDTO>> listarPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(notaService.listarPorAluno(alunoId));
    }

    // --- BOLETIM CONSOLIDADO POR PERÍODO LETIVO ---
    @GetMapping("/alunos/{alunoId}/boletim")
    @Operation(summary = "Boletim consolidado do aluno em um período letivo (com média ponderada por disciplina)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<BoletimResponseDTO> boletim(@PathVariable Long alunoId, @RequestParam("periodo") String periodoLetivo) {
        return ResponseEntity.ok(notaService.boletim(alunoId, periodoLetivo));
    }
}

