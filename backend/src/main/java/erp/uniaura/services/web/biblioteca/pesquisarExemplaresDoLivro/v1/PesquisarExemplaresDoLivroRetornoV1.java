package erp.uniaura.services.web.biblioteca.pesquisarExemplaresDoLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarExemplaresDoLivroRetornoV1 {

    @Schema(description = "Total de exemplares do livro, ignorando a paginação")
    private Long total;

    @Schema(description = "Exemplares da página pedida")
    private List<PesquisarExemplaresDoLivroExemplarRetornoV1> lista;
}
