package erp.academico.modules.documento.rest;

import erp.academico.modules.documento.dto.AnalisarDocumentoRequestDTO;
import erp.academico.modules.documento.dto.DocumentoAlunoResponseDTO;
import erp.academico.modules.documento.model.TipoDocumento;
import erp.academico.modules.documento.service.DocumentoAlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
@Tag(name = "Documentos do Aluno", description = "Upload e análise dos documentos exigidos na matrícula")
public class DocumentoAlunoController {

    private final DocumentoAlunoService documentoAlunoService;

    // --- ENVIA UM DOCUMENTO: O ALUNO ENVIA O SEU, OU A SECRETARIA ENVIA INFORMANDO O ALUNO ---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Envia um documento (upload) para um aluno")
    @PreAuthorize("hasAnyRole('ALUNO','SECRETARIA','COORDENADOR','ADMIN')")
    public ResponseEntity<DocumentoAlunoResponseDTO> enviar(
            @RequestParam(required = false) UUID alunoId,
            @RequestParam TipoDocumento tipo,
            @RequestParam("arquivo") MultipartFile arquivo,
            UriComponentsBuilder uriBuilder) {
        DocumentoAlunoResponseDTO criado = documentoAlunoService.enviar(alunoId, tipo, arquivo);
        URI uri = uriBuilder.path("/documentos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA OS DOCUMENTOS DE UM ALUNO ESPECÍFICO ---
    @GetMapping
    @Operation(summary = "Lista os documentos de um aluno")
    @PreAuthorize("hasAnyRole('SECRETARIA','COORDENADOR','ADMIN')")
    public ResponseEntity<Page<DocumentoAlunoResponseDTO>> listarPorAluno(
            @RequestParam UUID aluno, Pageable pageable) {
        return ResponseEntity.ok(documentoAlunoService.listarPorAluno(aluno, pageable));
    }

    // --- LISTA OS DOCUMENTOS DO ALUNO AUTENTICADO ---
    @GetMapping("/meus")
    @Operation(summary = "Lista os documentos do aluno autenticado")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Page<DocumentoAlunoResponseDTO>> meus(Pageable pageable) {
        return ResponseEntity.ok(documentoAlunoService.meus(pageable));
    }

    // --- APROVA OU REJEITA UM DOCUMENTO ENVIADO ---
    @PutMapping("/{id}/analise")
    @Operation(summary = "Aprova ou rejeita um documento enviado pelo aluno")
    @PreAuthorize("hasAnyRole('SECRETARIA','COORDENADOR','ADMIN')")
    public ResponseEntity<DocumentoAlunoResponseDTO> analisar(@PathVariable UUID id,
                                                                @Valid @RequestBody AnalisarDocumentoRequestDTO dto) {
        return ResponseEntity.ok(documentoAlunoService.analisar(id, dto));
    }

    // --- REMOVE UM DOCUMENTO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um documento (o próprio aluno só enquanto pendente)")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        documentoAlunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
