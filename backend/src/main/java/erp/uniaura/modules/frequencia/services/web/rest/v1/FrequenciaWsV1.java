package erp.uniaura.modules.frequencia.services.web.rest.v1;

import erp.uniaura.modules.frequencia.dto.AulaRequestDTO;
import erp.uniaura.modules.frequencia.dto.AulaResponseDTO;
import erp.uniaura.modules.frequencia.dto.ChamadaRequestDTO;
import erp.uniaura.modules.frequencia.dto.FrequenciaPercentualDTO;
import erp.uniaura.modules.frequencia.dto.FrequenciaResponseDTO;
import erp.uniaura.modules.frequencia.dto.FrequenciaResumoDTO;
import erp.uniaura.modules.frequencia.service.FrequenciaService;

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
@Tag(name = "Frequência", description = "Aulas, chamada e cálculo de frequência dos alunos")
public class FrequenciaWsV1 {

    private final FrequenciaService frequenciaService;

    // --- CRIA UMA AULA E GERA AS FREQUÊNCIAS AUTOMATICAMENTE ---
    @PostMapping("/aulas")
    @Operation(summary = "Cria uma aula e gera registros de frequência (presente=true) para todos os alunos matriculados")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<AulaResponseDTO> criarAula(@Valid @RequestBody AulaRequestDTO dto, UriComponentsBuilder uriBuilder) {
        AulaResponseDTO criada = frequenciaService.criarAula(dto);
        URI uri = uriBuilder.path("/aulas/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- LISTA OS REGISTROS DE PRESENÇA DA AULA ---
    @GetMapping("/aulas/{id}/frequencias")
    @Operation(summary = "Lista os registros de presença/falta da aula")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<List<FrequenciaResponseDTO>> listarFrequencias(@PathVariable Long id) {
        return ResponseEntity.ok(frequenciaService.listarFrequenciasDaAula(id));
    }

    // --- REGISTRA PRESENÇAS/FALTAS EM LOTE ---
    @PutMapping("/aulas/{id}/chamada")
    @Operation(summary = "Registra presenças e faltas da aula em lote")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<List<FrequenciaResponseDTO>> registrarChamada(@PathVariable Long id, @Valid @RequestBody ChamadaRequestDTO dto) {
        return ResponseEntity.ok(frequenciaService.registrarChamada(id, dto));
    }

    // --- PERCENTUAL DO ALUNO EM UMA DISCIPLINA ---
    @GetMapping("/alunos/{alunoId}/frequencia")
    @Operation(summary = "Percentual de frequência do aluno em uma disciplina específica")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<FrequenciaPercentualDTO> percentualPorDisciplina(@PathVariable Long alunoId,
            @RequestParam("disciplina") Long disciplinaId) {
        return ResponseEntity.ok(frequenciaService.calcularPercentualPresencaAluno(alunoId, disciplinaId));
    }

    // --- PERCENTUAL POR TODAS AS DISCIPLINAS DO ALUNO ---
    @GetMapping("/alunos/{alunoId}/frequencia/resumo")
    @Operation(summary = "Resumo geral do aluno (percentual por disciplina)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<FrequenciaResumoDTO> resumo(@PathVariable Long alunoId) {
        return ResponseEntity.ok(frequenciaService.resumoDoAluno(alunoId));
    }
}

