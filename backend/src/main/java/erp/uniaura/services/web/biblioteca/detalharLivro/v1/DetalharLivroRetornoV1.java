package erp.uniaura.services.web.biblioteca.detalharLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetalharLivroRetornoV1 {

    @Schema(description = "Identificador público do livro")
    private Long            uid;
    private String          isbn;
    private String          titulo;
    private String          autor;
    private String          editora;
    private Integer         anoPublicacao;
    private String          edicao;
    private Integer         paginas;
    private String          categoria;
    private String          sinopse;
    private String          capaUrl;
    private Long            totalExemplares;
    private Long            exemplaresDisponiveis;
    private LocalDateTime   criadoEm;
    private LocalDateTime   atualizadoEm;
}
