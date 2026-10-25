package erp.uniaura.services.web.biblioteca.alterarLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarLivroParamsV1 {

    @Schema(description = "Identificador público do livro a alterar")
    @NotNull(message = "O uid do livro é obrigatório.")
    private Long uid;

    @Schema(description = "ISBN do livro; único no acervo")
    @Size(max = 20, message = "O ISBN deve ter no máximo 20 caracteres.")
    private String isbn;

    @Schema(description = "Título do livro")
    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 300, message = "O título deve ter no máximo 300 caracteres.")
    private String titulo;

    @Schema(description = "Autor do livro")
    @NotBlank(message = "O autor é obrigatório.")
    @Size(max = 300, message = "O autor deve ter no máximo 300 caracteres.")
    private String autor;

    @Schema(description = "Editora")
    @Size(max = 200, message = "A editora deve ter no máximo 200 caracteres.")
    private String editora;

    @Schema(description = "Ano de publicação")
    private Integer anoPublicacao;

    @Schema(description = "Edição")
    @Size(max = 50, message = "A edição deve ter no máximo 50 caracteres.")
    private String edicao;

    @Schema(description = "Quantidade de páginas")
    private Integer paginas;

    @Schema(description = "Categoria")
    @Size(max = 100, message = "A categoria deve ter no máximo 100 caracteres.")
    private String categoria;

    @Schema(description = "Sinopse")
    private String sinopse;
}
