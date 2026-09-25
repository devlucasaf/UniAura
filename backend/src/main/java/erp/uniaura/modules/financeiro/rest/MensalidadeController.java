package erp.uniaura.modules.financeiro.rest;

import erp.uniaura.modules.financeiro.dto.MensalidadeRequestDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeResponseDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoRequestDTO;
import erp.uniaura.modules.financeiro.model.StatusMensalidade;
import erp.uniaura.modules.financeiro.service.MensalidadeService;

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
@RequestMapping("/mensalidades")
@RequiredArgsConstructor
@Tag(name = "Financeiro", description = "Mensalidades e pagamentos (simulados) dos alunos")
public class MensalidadeController {

    private final MensalidadeService mensalidadeService;

    // --- LISTA MENSALIDADES, COM FILTRO OPCIONAL POR ALUNO E STATUS ---
    @GetMapping
    @Operation(summary = "Lista mensalidades (opcionalmente filtrando por aluno e status)")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO','SECRETARIA')")
    public ResponseEntity<Page<MensalidadeResponseDTO>> listar(
            @RequestParam(required = false) UUID aluno,
            @RequestParam(required = false) StatusMensalidade status,
            Pageable pageable) {
        return ResponseEntity.ok(mensalidadeService.listar(aluno, status, pageable));
    }

    // --- LISTA AS MENSALIDADES DO ALUNO AUTENTICADO ---
    @GetMapping("/minhas")
    @Operation(summary = "Lista as mensalidades do aluno autenticado")
    @PreAuthorize("hasRole('ALUNO')")
    public ResponseEntity<Page<MensalidadeResponseDTO>> minhas(Pageable pageable) {
        return ResponseEntity.ok(mensalidadeService.minhas(pageable));
    }

    // --- BUSCA MENSALIDADE PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca mensalidade pelo ID")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO','SECRETARIA')")
    public ResponseEntity<MensalidadeResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(mensalidadeService.buscarPorId(id));
    }

    // --- CRIA UMA NOVA MENSALIDADE ---
    @PostMapping
    @Operation(summary = "Cria uma mensalidade para um aluno")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public ResponseEntity<MensalidadeResponseDTO> criar(@Valid @RequestBody MensalidadeRequestDTO dto,
                                                          UriComponentsBuilder uriBuilder) {
        MensalidadeResponseDTO criada = mensalidadeService.criar(dto);
        URI uri = uriBuilder.path("/mensalidades/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- ATUALIZA UMA MENSALIDADE EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma mensalidade ainda não paga")
    @PreAuthorize("hasAnyRole('ADMIN','FINANCEIRO')")
    public ResponseEntity<MensalidadeResponseDTO> atualizar(@PathVariable UUID id,
                                                              @Valid @RequestBody MensalidadeRequestDTO dto) {
        return ResponseEntity.ok(mensalidadeService.atualizar(id, dto));
    }

    // --- REMOVE UMA MENSALIDADE ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma mensalidade")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        mensalidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- REGISTRA O PAGAMENTO DE UMA MENSALIDADE ---
    @PostMapping("/{id}/pagamento")
    @Operation(summary = "Registra o pagamento de uma mensalidade")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MensalidadeResponseDTO> registrarPagamento(@PathVariable UUID id,
                                                                       @Valid @RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(mensalidadeService.registrarPagamento(id, dto));
    }
}
