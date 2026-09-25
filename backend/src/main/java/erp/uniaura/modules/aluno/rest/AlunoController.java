package erp.uniaura.modules.aluno.rest;

import erp.uniaura.modules.aluno.dto.AlunoRequestDTO;
import erp.uniaura.modules.aluno.dto.AlunoResponseDTO;
import erp.uniaura.modules.aluno.model.StatusAluno;
import erp.uniaura.modules.aluno.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "CRUD de alunos do sistema acadêmico")
public class AlunoController {

    private final AlunoService alunoService;

    // --- LISTA OS ALUNOS DE FORMA PAGINADA, PERMITINDO A FILTRAGEM OPCIONAL POR STATUS ---
    @GetMapping
    @Operation(summary = "Lista alunos paginados (opcionalmente filtrando por status)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<Page<AlunoResponseDTO>> listar(@RequestParam(required = false) StatusAluno status, Pageable pageable) {
        return ResponseEntity.ok(alunoService.listar(status, pageable));
    }

    // --- BUSCA UM ALUNO PELO SEU IDENTIFICADOR ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca aluno pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    // --- BUSCA UM ALUNO PELO SEU REGISTRO ACADÊMICO ---
    @GetMapping("/matricula/{matriculaRA}")
    @Operation(summary = "Busca aluno pela matrícula (RA)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA','PROFESSOR')")
    public ResponseEntity<AlunoResponseDTO> buscarPorMatricula(@PathVariable String matriculaRA) {
        return ResponseEntity.ok(alunoService.buscarPorMatricula(matriculaRA));
    }

    // --- CRIA UM NOVO ALUNO E RETORNA A LOCALIZAÇÃO DO RECURSO CRIADO ---
    @PostMapping
    @Operation(summary = "Cria um aluno (cria também o usuário associado com role ALUNO)")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<AlunoResponseDTO> criar(@Valid @RequestBody AlunoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        AlunoResponseDTO criado = alunoService.criar(dto);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA OS DADOS DE UM ALUNO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno existente")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(alunoService.atualizar(id, dto));
    }

    // --- REMOVE UM ALUNO PELO SEU IDENTIFICADOR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno")
    @PreAuthorize("hasAnyRole('ADMIN','COORDENADOR','SECRETARIA')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

