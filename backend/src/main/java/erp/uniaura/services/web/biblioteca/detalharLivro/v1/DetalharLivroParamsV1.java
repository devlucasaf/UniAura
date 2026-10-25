package erp.uniaura.services.web.biblioteca.detalharLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharLivroParamsV1 {

    @Schema(description = "Identificador público do livro")
    @NotNull(message = "O uid do livro é obrigatório.")
    private Long uid;
}
