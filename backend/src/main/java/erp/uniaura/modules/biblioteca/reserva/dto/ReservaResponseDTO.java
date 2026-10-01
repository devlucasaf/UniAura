package erp.uniaura.modules.biblioteca.reserva.dto;

import erp.uniaura.modules.biblioteca.reserva.model.StatusReserva;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private Long            id;
    private Long            livroId;
    private String          livroTitulo;
    private Long            usuarioId;
    private String          usuarioNome;
    private LocalDateTime   dataReserva;
    private StatusReserva   status;
    private Integer         posicaoFila;
}

