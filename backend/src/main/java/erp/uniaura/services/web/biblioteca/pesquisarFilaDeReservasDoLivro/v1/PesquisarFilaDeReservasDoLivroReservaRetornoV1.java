package erp.uniaura.services.web.biblioteca.pesquisarFilaDeReservasDoLivro.v1;

import erp.uniaura.persistence.entities.StatusReserva;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarFilaDeReservasDoLivroReservaRetornoV1 {

    @Schema(description = "Identificador público da reserva")
    private Long            uid;
    private Integer         posicaoFila;
    private String          usuarioNome;
    private LocalDateTime   dataReserva;
    private LocalDateTime   prazoRetirada;
    private StatusReserva   status;
}
