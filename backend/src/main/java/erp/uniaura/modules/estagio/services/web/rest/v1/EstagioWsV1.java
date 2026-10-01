package erp.uniaura.modules.estagio.services.web.rest.v1;

import erp.uniaura.modules.estagio.dto.EncerrarEstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.EstagioRequestDTO;
import erp.uniaura.modules.estagio.dto.EstagioResponseDTO;
import erp.uniaura.modules.estagio.model.StatusEstagio;
import erp.uniaura.modules.estagio.service.EstagioService;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Ws("/estagios")
@RequiredArgsConstructor
@Tag(name = "Estágio Supervisionado", description = "Abertura, acompanhamento e encerramento dos estágios dos alunos")
public class EstagioWsV1 {

    private final EstagioService estagioService;

    // --- ABRE UM NOVO ESTÁGIO PARA UM ALUNO ---
    @PostMapping
    @Operation(summary = "Abre um novo estágio para um aluno")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<EstagioResponseDTO> abrir(@Valid @RequestBody EstagioRequestDTO dto, UriComponentsBuilder uriBuilder) {
        EstagioResponseDTO criado = estagioService.abrir(dto);
        URI uri = uriBuilder.path("/estagios/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- LISTA ESTÁGIOS, COM FILTRO OPCIONAL POR ALUNO E STATUS ---
    @GetMapping
    @Operation(summary = "Lista estágios (opcionalmente filtrando por aluno e status)")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    public ResponseEntity<Page<EstagioResponseDTO>> listar(@RequestParam(required = false) Long aluno,
            @RequestParam(required = false) StatusEstagio status, Pageable pageable) {
        return ResponseEntity.ok(estagioService.listar(aluno, status, pageable));
    }

    // --- BUSCA O ESTÁGIO EM ANDAMENTO DO ALUNO AUTENTICADO ---
    @GetMapping("/meu")
    @Operation(summary = "Busca o estágio em andamento do aluno autenticado")
    @HasAuthority("ROLE_ALUNO")
    public ResponseEntity<EstagioResponseDTO> meuEstagio() {
        return ResponseEntity.ok(estagioService.meuEstagio());
    }

    // --- BUSCA ESTÁGIO PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca estágio pelo ID")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN", "ROLE_PROFESSOR"})
    public ResponseEntity<EstagioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estagioService.buscarPorId(id));
    }

    // --- ENCERRA UM ESTÁGIO (CONCLUÍDO, TRANCADO OU CANCELADO) ---
    @PutMapping("/{id}/encerramento")
    @Operation(summary = "Encerra um estágio (concluído, trancado ou cancelado)")
    @HasAnyAuthority({"ROLE_SECRETARIA", "ROLE_COORDENADOR", "ROLE_ADMIN"})
    public ResponseEntity<EstagioResponseDTO> encerrar(@PathVariable Long id, @Valid @RequestBody EncerrarEstagioRequestDTO dto) {
        return ResponseEntity.ok(estagioService.encerrar(id, dto));
    }
}
