package erp.uniaura.modules.tcc.services.web.rest.v1;

import erp.uniaura.modules.tcc.dto.AnalisarEntregaTccRequestDTO;
import erp.uniaura.modules.tcc.dto.EntregaTccResponseDTO;
import erp.uniaura.modules.tcc.model.TipoEntregaTcc;
import erp.uniaura.modules.tcc.service.EntregaTccService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/tccs/{tccId}/entregas")
@RequiredArgsConstructor
@Tag(name = "Entregas de TCC", description = "Envio e análise das entregas (projeto, parcial, versão final) do TCC")
public class EntregaTccWsV1 {

    private final EntregaTccService entregaTccService;

    // --- ENVIA UMA ENTREGA DO TCC ---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Envia uma entrega (projeto, parcial ou versão final) do TCC")
    @IsAuthenticated
    public ResponseEntity<EntregaTccResponseDTO> enviar(@PathVariable Long tccId, @RequestParam TipoEntregaTcc tipo,
            @RequestParam("arquivo") MultipartFile arquivo, UriComponentsBuilder uriBuilder) {
        EntregaTccResponseDTO criado = entregaTccService.enviar(tccId, tipo, arquivo);
        URI uri = uriBuilder.path("/tccs/{tccId}/entregas/{id}").buildAndExpand(tccId, criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA AS ENTREGAS DE UM TCC ---
    @GetMapping
    @Operation(summary = "Lista as entregas de um TCC")
    @IsAuthenticated
    public ResponseEntity<Page<EntregaTccResponseDTO>> listarPorTcc(@PathVariable Long tccId, Pageable pageable) {
        return ResponseEntity.ok(entregaTccService.listarPorTcc(tccId, pageable));
    }

    // --- APROVA OU REJEITA UMA ENTREGA DO TCC ---
    @PutMapping("/{id}/analise")
    @Operation(summary = "Aprova ou rejeita uma entrega do TCC")
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<EntregaTccResponseDTO> analisar(@PathVariable Long tccId, @PathVariable Long id,
            @Valid @RequestBody AnalisarEntregaTccRequestDTO dto) {
        return ResponseEntity.ok(entregaTccService.analisar(id, dto));
    }
}
