package erp.uniaura.modules.funcionario.services.web.rest.v1;

import erp.uniaura.modules.funcionario.dto.FuncionarioRequestDTO;
import erp.uniaura.modules.funcionario.dto.FuncionarioResponseDTO;
import erp.uniaura.modules.funcionario.model.CargoFuncionario;
import erp.uniaura.modules.funcionario.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
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

@Ws("/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionários", description = "CRUD de funcionários administrativos")
public class FuncionarioWsV1 {

    private final FuncionarioService funcionarioService;

    // --- LISTA FUNCIONÁRIOS ---
    @GetMapping
    @Operation(summary = "Lista funcionários paginados (opcionalmente filtrando por cargo)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Page<FuncionarioResponseDTO>> listar(@RequestParam(required = false) CargoFuncionario cargo, Pageable pageable) {
        return ResponseEntity.ok(funcionarioService.listar(cargo, pageable));
    }

    // --- BUSCA FUNCIONÁRIO PELO ID ---
    @GetMapping("/{id}")
    @Operation(summary = "Busca funcionário pelo ID")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    // --- CRIA UM FUNCIONÁRIO ---
    @PostMapping
    @Operation(summary = "Cria um funcionário (cria também o usuário com a role correspondente ao cargo)")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<FuncionarioResponseDTO> criar(@Valid @RequestBody FuncionarioRequestDTO dto, UriComponentsBuilder uriBuilder) {
        FuncionarioResponseDTO criado = funcionarioService.criar(dto);
        URI uri = uriBuilder.path("/funcionarios/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    // --- ATUALIZA UM FUNCIONÁRIO EXISTENTE ---
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um funcionário existente")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<FuncionarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO dto) {
        return ResponseEntity.ok(funcionarioService.atualizar(id, dto));
    }

    // --- REMOVE UM FUNCIONÁRIO ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um funcionário")
    @HasAnyAuthority({"ROLE_ADMIN", "ROLE_COORDENADOR", "ROLE_SECRETARIA"})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
