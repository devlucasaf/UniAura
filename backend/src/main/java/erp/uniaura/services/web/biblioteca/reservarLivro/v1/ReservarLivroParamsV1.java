package erp.uniaura.services.web.biblioteca.reservarLivro.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservarLivroParamsV1 {

    @Schema(description = "Identificador público do livro a reservar")
    @NotNull(message = "O uid do livro é obrigatório.")
    private Long livroUid;
}
