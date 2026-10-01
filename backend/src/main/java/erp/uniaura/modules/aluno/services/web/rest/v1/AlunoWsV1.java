package erp.uniaura.modules.aluno.services.web.rest.v1;

import erp.uniaura.modules.aluno.dto.AlunoRequestDTO;
import erp.uniaura.modules.aluno.dto.AlunoResponseDTO;
import erp.uniaura.modules.aluno.model.StatusAluno;
import erp.uniaura.modules.aluno.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
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

@Ws("/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "CRUD de alunos do sistema acadêmico")
public class AlunoWsV1 {

    private final AlunoService alunoService;

    // --- LISTA OS ALUNOS DE FORMA PAGINADA, PERMITINDO A FILTRAGEM OPCIONAL POR STATUS ---
    @GetMapping
    @Operation(summary = "Lista alunos paginados (opcionalmente filtrando por status)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<AlunoResponseDTO>> listar(@RequestParam(required = false) StatusAluno status, Pageable pageable) {
        return ResponseEntity.ok(alunoService.listar(status, pageable));
    }

    // --- BUSCA UM ALUNO PELO SEU IDENTIFICADOR ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca aluno pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    // --- BUSCA UM ALUNO PELO SEU REGISTRO ACADÊMICO ---
    @GetMapping("/matricula/{matriculaRA}")
    @Operation(summary = "Busca aluno pela matrícula (RA)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<AlunoResponseDTO> buscarPorMatricula(@PathVariable String matriculaRA) {
        return ResponseEntity.ok(alunoService.buscarPorMatricula(matriculaRA));
    }

    // --- CRIA UM NOVO ALUNO E RETORNA A LOCALIZAÇÃO DO RECURSO CRIADO ---
    @PostMapping
    @Operation(summary = "Cria um aluno (cria também o usuário associado com role ALUNO)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<AlunoResponseDTO> criar(@Valid @RequestBody AlunoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        AlunoResponseDTO alunoCriadoDTO = alunoService.criar(dto);
        URI uri = uriBuilder.path("/alunos/{id}").buildAndExpand(alunoCriadoDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(alunoCriadoDTO);
    }

    // --- ATUALIZA OS DADOS DE UM ALUNO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<AlunoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AlunoRequestDTO dto) {
        return ResponseEntity.ok(alunoService.atualizar(id, dto));
    }

    // --- REMOVE UM ALUNO PELO SEU IDENTIFICADOR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

