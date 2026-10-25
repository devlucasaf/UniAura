package erp.uniaura.services.web.biblioteca.pesquisarLivros.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarLivrosRetornoV1 {

    @Schema(description = "Total de livros que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Livros da página pedida")
    private List<PesquisarLivrosLivroRetornoV1> lista;
}
