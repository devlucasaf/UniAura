package erp.uniaura.modules.curso.services.web.rest.v1;

import erp.uniaura.modules.curso.dto.CursoRequestDTO;
import erp.uniaura.modules.curso.dto.CursoResponseDTO;
import erp.uniaura.modules.curso.model.NivelCurso;
import erp.uniaura.modules.curso.service.CursoService;
import erp.uniaura.modules.disciplina.dto.DisciplinaResponseDTO;
import erp.uniaura.modules.disciplina.service.DisciplinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
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

@Ws("/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "CRUD de cursos oferecidos pela instituição")
public class CursoWsV1 {

    // --- DEPENDÊNCIAS INJETADAS VIA CONSTRUTOR ---
    private final CursoService cursoService;
    private final DisciplinaService disciplinaService;

    // --- LISTA PAGINADA COM FILTRO ---
    @GetMapping
    @Operation(summary = "Lista cursos paginados (opcionalmente filtrando por nível)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<CursoResponseDTO>> listar(@RequestParam(required = false) NivelCurso nivel, Pageable pageable) {
        return ResponseEntity.ok(cursoService.listar(nivel, pageable));
    }

    // --- BUSCA POR ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca curso pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<CursoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    // --- CRIA UM NOVO CURSO ---
    @PostMapping
    @Operation(summary = "Cria um novo curso")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<CursoResponseDTO> criar(@Valid @RequestBody CursoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        CursoResponseDTO criado = cursoService.criar(dto);
        URI uri = uriBuilder.path("/cursos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM CURSO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um curso existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR"})
    public ResponseEntity<CursoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.ok(cursoService.atualizar(id, dto));
    }

    // --- REMOVE O CURSO  ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um curso")
    @HasAuthority("ROLE_ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cursoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA AS DISCIPLINAS DO CURSO ---
    @GetMapping("/{id}/disciplinas")
    @Operation(summary = "Lista as disciplinas do curso")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<DisciplinaResponseDTO>> listarDisciplinas(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(disciplinaService.listarPorCurso(id, pageable));
    }
}
