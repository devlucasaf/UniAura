package erp.uniaura.modules.documento.services.web.rest.v1;

import erp.uniaura.modules.documento.dto.AnalisarDocumentoRequestDTO;
import erp.uniaura.modules.documento.dto.DocumentoAlunoResponseDTO;
import erp.uniaura.modules.documento.model.TipoDocumento;
import erp.uniaura.modules.documento.service.DocumentoAlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.HasAuthority;
import cloudsupport.security.IsAuthenticated;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Ws("/documentos")
@RequiredArgsConstructor
@Tag(name = "Documentos do Aluno", description = "Upload e análise dos documentos exigidos na matrícula")
public class DocumentoAlunoWsV1 {

    private final DocumentoAlunoService documentoAlunoService;

    // --- ENVIA UM DOCUMENTO---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Envia um documento (upload) para um aluno")
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<DocumentoAlunoResponseDTO> enviar(@RequestParam(required = false) Long alunoId,
            @RequestParam TipoDocumento tipo, @RequestParam("arquivo") MultipartFile arquivo, UriComponentsBuilder uriBuilder) {
        DocumentoAlunoResponseDTO criado = documentoAlunoService.enviar(alunoId, tipo, arquivo);
        URI uri = uriBuilder.path("/documentos/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA OS DOCUMENTOS DE UM ALUNO ESPECÍFICO ---
    @GetMapping
    @Operation(summary = "Lista os documentos de um aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<Page<DocumentoAlunoResponseDTO>> listarPorAluno(
            @RequestParam Long aluno, Pageable pageable) {
        return ResponseEntity.ok(documentoAlunoService.listarPorAluno(aluno, pageable));
    }

    // --- LISTA OS DOCUMENTOS DO ALUNO AUTENTICADO ---
    @GetMapping("/meus")
    @Operation(summary = "Lista os documentos do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<Page<DocumentoAlunoResponseDTO>> meus(Pageable pageable) {
        return ResponseEntity.ok(documentoAlunoService.meus(pageable));
    }

    // --- HISTÓRICO DE TODAS AS VERSÕES ENVIADAS DE UM TIPO DE DOCUMENTO DO ALUNO ---
    @GetMapping("/historico")
    @Operation(summary = "Lista o histórico de versões enviadas de um tipo de documento do aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<List<DocumentoAlunoResponseDTO>> historico(
            @RequestParam Long aluno, @RequestParam TipoDocumento tipo) {
        return ResponseEntity.ok(documentoAlunoService.historico(aluno, tipo));
    }

    // --- APROVA OU REJEITA UM DOCUMENTO ENVIADO ---
    @PutMapping("/{id}/analise")
    @Operation(summary = "Aprova ou rejeita um documento enviado pelo aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<DocumentoAlunoResponseDTO> analisar(@PathVariable Long id,
              @Valid @RequestBody AnalisarDocumentoRequestDTO dto) {
        return ResponseEntity.ok(documentoAlunoService.analisar(id, dto));
    }

    // --- REMOVE UM DOCUMENTO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um documento (o próprio aluno só enquanto pendente)")
    @IsAuthenticated
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        documentoAlunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
