package erp.uniaura.modules.prematricula.services.web.rest.v1;

import cloudsupport.services.web.Ws;

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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/pre-matricula")
@RequiredArgsConstructor
@Tag(name = "Pré-matrícula", description = "Auto-cadastro público de candidatos no Portal do Aluno")
public class PreMatriculaWsV1 {

    private final PreMatriculaService preMatriculaService;

    // --- QUALQUER VISITANTE PODE CRIAR SUA MATRÍCULA E CONTA DE ACESSO ---
    @PostMapping
    @Operation(summary = "Realiza a pré-matrícula pública e cria o acesso do aluno ao portal")
    public ResponseEntity<PreMatriculaResponseDTO> matricular(@Valid
              @RequestBody PreMatriculaRequestDTO dto, UriComponentsBuilder uriBuilder) {
        PreMatriculaResponseDTO preMatriculaCriadaDTO = preMatriculaService.matricular(dto);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(preMatriculaCriadaDTO.getAlunoId()).toUri();
        return ResponseEntity.created(uri).body(preMatriculaCriadaDTO);
    }
}
