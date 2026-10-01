package erp.uniaura.modules.biblioteca.reserva.services.web.rest.v1;

import erp.uniaura.modules.biblioteca.reserva.dto.ReservaRequestDTO;
import erp.uniaura.modules.biblioteca.reserva.dto.ReservaResponseDTO;
import erp.uniaura.modules.biblioteca.reserva.service.ReservaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import cloudsupport.security.HasAnyAuthority;
import cloudsupport.security.IsAuthenticated;
import cloudsupport.services.web.Ws;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Ws("/biblioteca/reservas")
@RequiredArgsConstructor
@Tag(name = "Biblioteca - Reservas", description = "Reservas de livros com fila")
public class ReservaWsV1 {

    private final ReservaService reservaService;

    // --- CRIA UMA RESERVA PARA O USUÁRIO AUTENTICADO ---
    @PostMapping
    @Operation(summary = "Cria uma reserva para o usuário autenticado")
    @HasAnyAuthority({"ROLE_ALUNO", "ROLE_PROFESSOR"})
    public ResponseEntity<ReservaResponseDTO> reservar(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.reservar(dto));
    }

    // --- CANCELA UMA RESERVA PELO SEU IDENTIFICADOR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela reserva")
    @IsAuthenticated
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    // --- LISTA A FILA DE RESERVAS ATIVAS DE UM LIVRO ---
    @GetMapping("/livro/{livroId}/fila")
    @Operation(summary = "Fila de reservas ativas de um livro")
    @HasAnyAuthority({"ROLE_BIBLIOTECARIO", "ROLE_ADMIN"})
    public ResponseEntity<List<ReservaResponseDTO>> filaDoLivro(@PathVariable Long livroId) {
        return ResponseEntity.ok(reservaService.filaDoLivro(livroId));
    }

    // --- LISTA AS RESERVAS ASSOCIADAS A UM USUÁRIO ---
    @GetMapping("/usuario/{usuarioId}")
    @IsAuthenticated
    public ResponseEntity<List<ReservaResponseDTO>> doUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.reservasDoUsuario(usuarioId));
    }
}

