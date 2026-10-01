package erp.uniaura.modules.financeiro.services.web.rest.v1;

import erp.uniaura.modules.financeiro.dto.BoletoResponseDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeRequestDTO;
import erp.uniaura.modules.financeiro.dto.MensalidadeResponseDTO;
import erp.uniaura.modules.financeiro.dto.PagamentoRequestDTO;
import erp.uniaura.modules.financeiro.dto.PixResponseDTO;
import erp.uniaura.modules.financeiro.model.StatusMensalidade;
import erp.uniaura.modules.financeiro.service.MensalidadeService;

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

@Ws("/mensalidades")
@RequiredArgsConstructor
@Tag(name = "Financeiro", description = "Mensalidades e pagamentos (simulados) dos alunos")
public class MensalidadeWsV1 {

    private final MensalidadeService mensalidadeService;

    // --- LISTA MENSALIDADES, COM FILTRO OPCIONAL POR ALUNO E STATUS ---
    @GetMapping
    @Operation(summary = "Lista mensalidades (opcionalmente filtrando por aluno e status)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA"})
    public ResponseEntity<Page<MensalidadeResponseDTO>> listar(@RequestParam(required = false) Long aluno,
            @RequestParam(required = false) StatusMensalidade status, Pageable pageable) {
        return ResponseEntity.ok(mensalidadeService.listar(aluno, status, pageable));
    }

    // --- LISTA AS MENSALIDADES DO ALUNO AUTENTICADO ---
    @GetMapping("/minhas")
    @Operation(summary = "Lista as mensalidades do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<Page<MensalidadeResponseDTO>> minhas(Pageable pageable) {
        return ResponseEntity.ok(mensalidadeService.minhas(pageable));
    }

    // --- BUSCA MENSALIDADE PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca mensalidade pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO", "ROLE_SECRETARIA"})
    public ResponseEntity<MensalidadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mensalidadeService.buscarPorId(id));
    }

    // --- CRIA UMA NOVA MENSALIDADE ---
    @PostMapping
    @Operation(summary = "Cria uma mensalidade para um aluno")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    public ResponseEntity<MensalidadeResponseDTO> criar(@Valid @RequestBody MensalidadeRequestDTO dto, UriComponentsBuilder uriBuilder) {
        MensalidadeResponseDTO criada = mensalidadeService.criar(dto);
        URI uri = uriBuilder.path("/mensalidades/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    // --- ATUALIZA UMA MENSALIDADE EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma mensalidade ainda não paga")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_FINANCEIRO"})
    public ResponseEntity<MensalidadeResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody MensalidadeRequestDTO dto) {
        return ResponseEntity.ok(mensalidadeService.atualizar(id, dto));
    }

    // --- REMOVE UMA MENSALIDADE ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma mensalidade")
    @HasAuthority("ROLE_ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        mensalidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // --- REGISTRA O PAGAMENTO DE UMA MENSALIDADE ---
    @PostMapping("/{id}/pagamento")
    @Operation(summary = "Registra o pagamento de uma mensalidade")
    @IsAuthenticated
    public ResponseEntity<MensalidadeResponseDTO> registrarPagamento(@PathVariable Long id, @Valid @RequestBody PagamentoRequestDTO dto) {
        return ResponseEntity.ok(mensalidadeService.registrarPagamento(id, dto));
    }

    // --- GERA UM PIX FICTÍCIO (COPIA E COLA) PARA A TELA DE PAGAMENTO ---
    @GetMapping("/{id}/pix")
    @Operation(summary = "Gera um PIX fictício (copia e cola) para pagamento da mensalidade")
    @IsAuthenticated
    public ResponseEntity<PixResponseDTO> gerarPix(@PathVariable Long id) {
        return ResponseEntity.ok(mensalidadeService.gerarPix(id));
    }

    // --- GERA UM BOLETO FICTÍCIO PARA A TELA DE PAGAMENTO ---
    @GetMapping("/{id}/boleto")
    @Operation(summary = "Gera um boleto fictício para pagamento da mensalidade")
    @IsAuthenticated
    public ResponseEntity<BoletoResponseDTO> gerarBoleto(@PathVariable Long id) {
        return ResponseEntity.ok(mensalidadeService.gerarBoleto(id));
    }
}
