package erp.uniaura.services.web.biblioteca.reservarLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservarLivroRetornoV1 {

    @Schema(description = "Identificador público da reserva")
    private Long uid;

    @Schema(description = "Posição do usuário na fila de reservas do livro")
    private Integer posicaoFila;
}
