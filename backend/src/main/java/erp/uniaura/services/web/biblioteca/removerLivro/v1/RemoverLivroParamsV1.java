package erp.uniaura.services.web.biblioteca.removerLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverLivroParamsV1 {

    @Schema(description = "Identificador público do livro a remover")
    @NotNull(message = "O uid do livro é obrigatório.")
    private Long uid;
}
