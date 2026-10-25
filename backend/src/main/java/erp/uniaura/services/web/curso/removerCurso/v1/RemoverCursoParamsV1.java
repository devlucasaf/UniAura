package erp.uniaura.services.web.curso.removerCurso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverCursoParamsV1 {

    @Schema(description = "Identificador público do curso a remover")
    @NotNull(message = "O uid do curso é obrigatório.")
    private Long uid;
}
