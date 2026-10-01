package erp.uniaura.modules.material.services.web.rest.v1;

import erp.uniaura.modules.material.dto.MaterialRequestDTO;
import erp.uniaura.modules.material.dto.MaterialResponseDTO;
import erp.uniaura.modules.material.service.MaterialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/materiais")
@RequiredArgsConstructor
@Tag(name = "Materiais", description = "Materiais didáticos das disciplinas (PDF, vídeo, link, apresentação)")
public class MaterialWsV1 {

    private final MaterialService materialService;

    // --- BUSCA MATERIAL PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca material pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    public ResponseEntity<MaterialResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.buscarMaterialPorId(id));
    }

    // --- CRIA MATERIAL ---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Cria material (upload de arquivo ou link externo)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<MaterialResponseDTO> criar(@Valid @RequestPart("dados") MaterialRequestDTO dto,
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo, UriComponentsBuilder uriBuilder) {
        MaterialResponseDTO criado = materialService.criarMaterialTurma(dto, arquivo);
        URI uri = uriBuilder.path("/materiais/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA METADADOS ---
    @PutMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Atualiza material existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<MaterialResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestPart("dados") MaterialRequestDTO dto,
            @RequestPart(value = "arquivo", required = false) MultipartFile arquivo) {
        return ResponseEntity.ok(materialService.atualizarMaterial(id, dto, arquivo));
    }

    // --- REMOVE O MATERIAL ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um material")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_PROFESSOR"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        materialService.deletarMaterial(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA MATERIAIS DA TURMA + DISCIPLINA ---
    @GetMapping("/turmas/{turmaId}/disciplinas/{disciplinaId}")
    @Operation(summary = "Lista materiais por turma e disciplina")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA", "ROLE_PROFESSOR", "ROLE_ALUNO", "ROLE_RESPONSAVEL"})
    public ResponseEntity<Page<MaterialResponseDTO>> listarPorTurmaDisciplina(@PathVariable Long turmaId,
            @PathVariable Long disciplinaId, Pageable pageable) {
        return ResponseEntity.ok(materialService.listarPorTurmaDisciplina(turmaId, disciplinaId, pageable));
    }
}

