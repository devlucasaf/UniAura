package erp.uniaura.modules.avaliacao.services.web.rest.v1;

import erp.uniaura.modules.avaliacao.dto.AvaliacaoDocenteRequestDTO;
import erp.uniaura.modules.avaliacao.dto.ComentarioAvaliacaoResponseDTO;
import erp.uniaura.modules.avaliacao.dto.MediaAvaliacaoResponseDTO;
import erp.uniaura.modules.avaliacao.service.AvaliacaoDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Ws("/avaliacoes-docente")
@RequiredArgsConstructor
@Tag(name = "Avaliação Institucional", description = "Avaliação de professores pelos alunos (didática, pontualidade, disponibilidade)")
public class AvaliacaoDocenteWsV1 {

    private final AvaliacaoDocenteService avaliacaoDocenteService;

    // --- REGISTRA A AVALIAÇÃO DO ALUNO AUTENTICADO SOBRE UMA TURMA/DISCIPLINA ---
    @PostMapping
    @Operation(summary = "Registra a avaliação do aluno autenticado sobre o professor de uma turma/disciplina")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<Void> avaliar(@Valid @RequestBody AvaliacaoDocenteRequestDTO dto) {
        avaliacaoDocenteService.avaliar(dto);
        return ResponseEntity.noContent().build();
    }

    // --- MÉDIA DAS AVALIAÇÕES DE UMA TURMA/DISCIPLINA ---
    @GetMapping("/turma-disciplina/{id}/media")
    @Operation(summary = "Média das avaliações de uma turma/disciplina")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<MediaAvaliacaoResponseDTO> mediaPorTurmaDisciplina(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoDocenteService.mediaPorTurmaDisciplina(id));
    }

    // --- COMENTÁRIOS ANÔNIMOS DE UMA TURMA/DISCIPLINA ---
    @GetMapping("/turma-disciplina/{id}/comentarios")
    @Operation(summary = "Lista os comentários anônimos deixados pelos alunos sobre uma turma/disciplina")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<List<ComentarioAvaliacaoResponseDTO>> comentarios(@PathVariable Long id) {
        return ResponseEntity.ok(avaliacaoDocenteService.comentarios(id));
    }

    // --- MÉDIA CONSOLIDADA DE UM PROFESSOR ---
    @GetMapping("/professor/{professorId}/media")
    @Operation(summary = "Média consolidada de todas as turmas/disciplinas de um professor")
    @HasAnyAuthority({"ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<MediaAvaliacaoResponseDTO> mediaPorProfessor(@PathVariable Long professorId) {
        return ResponseEntity.ok(avaliacaoDocenteService.mediaPorProfessor(professorId));
    }

    // --- MÉDIA CONSOLIDADA DO PROFESSOR AUTENTICADO ---
    @GetMapping("/meu-desempenho")
    @Operation(summary = "Média consolidada do professor autenticado")
    @HasAuthority("ROLE_PROFESSOR")
    public ResponseEntity<MediaAvaliacaoResponseDTO> meuDesempenho() {
        return ResponseEntity.ok(avaliacaoDocenteService.meuDesempenho());
    }
}
