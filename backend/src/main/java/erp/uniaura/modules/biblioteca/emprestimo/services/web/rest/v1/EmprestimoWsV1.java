package erp.uniaura.modules.biblioteca.emprestimo.services.web.rest.v1;

import erp.uniaura.modules.biblioteca.emprestimo.dto.EmprestimoRequestDTO;
import erp.uniaura.modules.biblioteca.emprestimo.dto.EmprestimoResponseDTO;
import erp.uniaura.modules.biblioteca.emprestimo.service.EmprestimoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Ws("/biblioteca/emprestimos")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Empréstimos", description = "Empréstimos, devoluções e renovações")
public class EmprestimoWsV1 {

    private final EmprestimoService emprestimoService;

    // --- REGISTRA UM NOVO EMPRÉSTIMO ---
    @PostMapping
    @Operation(summary = "Registra um novo empréstimo")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    public ResponseEntity<EmprestimoResponseDTO> registrar(@Valid @RequestBody EmprestimoRequestDTO dto) {
        return ResponseEntity.ok(emprestimoService.registrar(dto));
    }

    // --- REGISTRA A DEVOLUÇÃO DE UM EMPRÉSTIMO PELO SEU IDENTIFICADOR ---
    @PostMapping("/{id}/devolver")
    @Operation(summary = "Registra devolução do empréstimo (gera multa se atrasado)")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    public ResponseEntity<EmprestimoResponseDTO> devolver(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.devolver(id));
    }

    // --- RENOVA UM EMPRÉSTIMO PELO SEU IDENTIFICADOR ---
    @PostMapping("/{id}/renovar")
    @Operation(summary = "Renova empréstimo respeitando limite e fila de reservas")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN", "ROLE_ALUNO", "ROLE_PROFESSOR"})
    public ResponseEntity<EmprestimoResponseDTO> renovar(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.renovar(id));
    }

    // --- BUSCA UM EMPRÉSTIMO PELO SEU IDENTIFICADOR ---
    @GetMapping("/{id}")
    @IsAuthenticated
    public ResponseEntity<EmprestimoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(emprestimoService.buscarPorId(id));
    }

    // --- LISTA OS EMPRÉSTIMOS DE UM USUÁRIO UTILIZANDO PAGINAÇÃO ---
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista empréstimos de um usuário")
    @IsAuthenticated
    public ResponseEntity<Page<EmprestimoResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId, Pageable pageable) {
        return ResponseEntity.ok(emprestimoService.listarPorUsuario(usuarioId, pageable));
    }
}

