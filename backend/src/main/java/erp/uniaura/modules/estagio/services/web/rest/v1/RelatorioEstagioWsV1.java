package erp.uniaura.modules.estagio.services.web.rest.v1;

import erp.uniaura.modules.estagio.dto.AnalisarRelatorioEstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.RelatorioEstagioResponseDTO;
import erp.uniaura.modules.estagio.service.RelatorioEstagioService;

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

@Ws("/estagios/{estagioId}/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios de Estágio", description = "Entregas periódicas de horas cumpridas no estágio")
public class RelatorioEstagioWsV1 {

    private final RelatorioEstagioService relatorioEstagioService;

    // --- ENVIA UM RELATÓRIO PERIÓDICO DE HORAS CUMPRIDAS ---
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @Operation(summary = "Envia um relatório periódico de horas cumpridas no estágio")
    @IsAuthenticated
    public ResponseEntity<RelatorioEstagioResponseDTO> enviar(@PathVariable Long estagioId,
            @RequestParam String periodoReferencia, @RequestParam Integer horasRegistradas,
            @RequestParam(required = false) String descricaoAtividades,
            @RequestParam(value = "comprovante", required = false) MultipartFile comprovante,
            UriComponentsBuilder uriBuilder) {
        RelatorioEstagioResponseDTO criado = relatorioEstagioService.enviar(
                estagioId, periodoReferencia, horasRegistradas, descricaoAtividades, comprovante);
        URI uri = uriBuilder.path("/estagios/{estagioId}/relatorios/{id}")
                .buildAndExpand(estagioId, criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA OS RELATÓRIOS ENVIADOS PARA UM ESTÁGIO ---
    @GetMapping
    @Operation(summary = "Lista os relatórios enviados para um estágio")
    @IsAuthenticated
    public ResponseEntity<Page<RelatorioEstagioResponseDTO>> listarPorEstagio(@PathVariable Long estagioId, Pageable pageable) {
        return ResponseEntity.ok(relatorioEstagioService.listarPorEstagio(estagioId, pageable));
    }

    // --- APROVA OU REJEITA UM RELATÓRIO DE ESTÁGIO ---
    @PutMapping("/{id}/analise")
    @Operation(summary = "Aprova ou rejeita um relatório de estágio")
    @HasAnyAuthority({"ROLE_PROFESSOR", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<RelatorioEstagioResponseDTO> analisar(@PathVariable Long estagioId, @PathVariable Long id,
            @Valid @RequestBody AnalisarRelatorioEstagioRequestDTO dto) {
        return ResponseEntity.ok(relatorioEstagioService.analisar(id, dto));
    }
}
