package erp.uniaura.modules.responsavel.services.web.rest.v1;

import erp.uniaura.modules.responsavel.dto.ResponsavelAlunoResponseDTO;
import erp.uniaura.modules.responsavel.dto.ResponsavelRequestDTO;
import erp.uniaura.modules.responsavel.dto.ResponsavelResponseDTO;
import erp.uniaura.modules.responsavel.dto.VincularAlunoRequestDTO;
import erp.uniaura.modules.responsavel.service.ResponsavelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/responsaveis")
@RequiredArgsConstructor
@Tag(name = "Responsáveis", description = "CRUD de responsáveis e vínculos com alunos")
public class ResponsavelWsV1 {

    private final ResponsavelService responsavelService;

    // --- LISTA RESPONSÁVEIS PAGINADOS ---
    @GetMapping
    @Operation(summary = "Lista responsáveis paginados")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Page<ResponsavelResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok(responsavelService.listar(pageable));
    }

    // --- BUSCA RESPONSÁVEL PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca responsável pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ResponsavelResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(responsavelService.buscarPorId(id));
    }

    // --- CRIA UM RESPONSÁVEL ---
    @PostMapping
    @Operation(summary = "Cria um responsável (cria também o usuário associado com role RESPONSAVEL)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ResponsavelResponseDTO> criar(@Valid
            @RequestBody ResponsavelRequestDTO dto, UriComponentsBuilder uriBuilder) {
        ResponsavelResponseDTO criado = responsavelService.criar(dto);
        URI uri = uriBuilder.path("/responsaveis/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM RESPONSÁVEL EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um responsável existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<ResponsavelResponseDTO> atualizar(@PathVariable Long id,
                @Valid @RequestBody ResponsavelRequestDTO dto) {
        return ResponseEntity.ok(responsavelService.atualizar(id, dto));
    }

    // --- REMOVE UM RESPONSÁVEL ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um responsável")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        responsavelService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA OS ALUNOS VINCULADOS AO RESPONSÁVEL ---
    @GetMapping("/{id}/alunos")
    @Operation(summary = "Lista os alunos vinculados ao responsável")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<List<ResponsavelAlunoResponseDTO>> listarAlunos(@PathVariable Long id) {
        return ResponseEntity.ok(responsavelService.listarAlunos(id));
    }

    // --- VINCULA UM ALUNO AO RESPONSÁVEL ---
    @PostMapping("/{id}/alunos")
    @Operation(summary = "Vincula um aluno ao responsável")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_SECRETARIA"})
    public ResponseEntity<ResponsavelAlunoResponseDTO> vincularAluno(@PathVariable Long id,
            @Valid @RequestBody VincularAlunoRequestDTO dto) {
        return ResponseEntity.status(201).body(responsavelService.vincularAluno(id, dto));
    }

    // --- REMOVE O VÍNCULO ENTRE RESPONSÁVEL E ALUNO ---
    @DeleteMapping("/{id}/alunos/{alunoId}")
    @Operation(summary = "Remove o vínculo entre responsável e aluno")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> desvincularAluno(@PathVariable Long id, @PathVariable Long alunoId) {
        responsavelService.desvincularAluno(id, alunoId);
        return ResponseEntity.noContent().build();
    }
}
