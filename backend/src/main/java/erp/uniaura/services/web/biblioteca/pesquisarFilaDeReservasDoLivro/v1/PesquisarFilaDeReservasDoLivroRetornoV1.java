package erp.uniaura.services.web.biblioteca.pesquisarFilaDeReservasDoLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarFilaDeReservasDoLivroRetornoV1 {

    @Schema(description = "Total de reservas aguardando na fila, ignorando a paginação")
    private Long total;

    @Schema(description = "Reservas da página pedida, na ordem da fila")
    private List<PesquisarFilaDeReservasDoLivroReservaRetornoV1> lista;
}
