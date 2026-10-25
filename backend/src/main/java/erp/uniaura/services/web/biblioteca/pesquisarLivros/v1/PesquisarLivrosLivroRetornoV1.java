package erp.uniaura.services.web.biblioteca.pesquisarLivros.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarLivrosLivroRetornoV1 {

    @Schema(description = "Identificador público do livro")
    private Long            uid;
    private String          isbn;
    private String          titulo;
    private String          autor;
    private String          categoria;
    private String          capaUrl;
    private Long            totalExemplares;
    private Long            exemplaresDisponiveis;
    private LocalDateTime   criadoEm;
}
