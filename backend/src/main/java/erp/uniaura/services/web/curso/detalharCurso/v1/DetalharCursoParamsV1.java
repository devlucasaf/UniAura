package erp.uniaura.services.web.curso.detalharCurso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharCursoParamsV1 {

    @Schema(description = "Identificador público do curso")
    @NotNull(message = "O uid do curso é obrigatório.")
    private Long uid;
}
