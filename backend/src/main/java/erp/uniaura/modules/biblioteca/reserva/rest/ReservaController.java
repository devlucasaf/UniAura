package erp.uniaura.modules.biblioteca.reserva.rest;

import erp.uniaura.modules.biblioteca.reserva.dto.ReservaRequestDTO;
import erp.uniaura.modules.biblioteca.reserva.dto.ReservaResponseDTO;
import erp.uniaura.modules.biblioteca.reserva.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/biblioteca/reservas")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Reservas", description = "Reservas de livros com fila")
public class ReservaController {

    private final ReservaService reservaService;

    // --- CRIA UMA RESERVA PARA O USUÁRIO AUTENTICADO ---
    @PostMapping
    @Operation(summary = "Cria uma reserva para o usuário autenticado")
    @PreAuthorize("hasAnyRole('ALUNO','PROFESSOR')")
    public ResponseEntity<ReservaResponseDTO> reservar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.reservar(dto));
    }

    // --- CANCELA UMA RESERVA PELO SEU IDENTIFICADOR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela reserva")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> cancelar(@PathVariable UUID id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA A FILA DE RESERVAS ATIVAS DE UM LIVRO ---
    @GetMapping("/livro/{livroId}/fila")
    @Operation(summary = "Fila de reservas ativas de um livro")
    @PreAuthorize("hasAnyRole('BIBLIOTECARIO','ADMIN')")
    public ResponseEntity<List<ReservaResponseDTO>> filaDoLivro(@PathVariable UUID livroId) {
        return ResponseEntity.ok(reservaService.filaDoLivro(livroId));
    }

    // --- LISTA AS RESERVAS ASSOCIADAS A UM USUÁRIO ---
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReservaResponseDTO>> doUsuario(@PathVariable UUID usuarioId) {
        return ResponseEntity.ok(reservaService.reservasDoUsuario(usuarioId));
    }
}

