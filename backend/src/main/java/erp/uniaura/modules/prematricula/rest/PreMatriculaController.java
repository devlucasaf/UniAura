package erp.uniaura.modules.prematricula.rest;

import erp.uniaura.modules.prematricula.dto.PreMatriculaRequestDTO;
import erp.uniaura.modules.prematricula.dto.PreMatriculaResponseDTO;
import erp.uniaura.modules.prematricula.service.PreMatriculaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pre-matricula")
@RequiredArgsConstructor
@Tag(name = "Pré-matrícula", description = "Auto-cadastro público de candidatos no Portal do Aluno")
public class PreMatriculaController {

    private final PreMatriculaService preMatriculaService;

    // --- QUALQUER VISITANTE PODE CRIAR SUA MATRÍCULA E CONTA DE ACESSO ---
    @PostMapping
    @Operation(summary = "Realiza a pré-matrícula pública e cria o acesso do aluno ao portal")
    public ResponseEntity<PreMatriculaResponseDTO> matricular(@Valid @RequestBody PreMatriculaRequestDTO dto,
                                                                UriComponentsBuilder uriBuilder) {
        PreMatriculaResponseDTO criado = preMatriculaService.matricular(dto);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(criado.getAlunoId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }
}
