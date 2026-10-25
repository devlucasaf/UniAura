package erp.uniaura.services.web.matricula.detalharMatricula.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharMatriculaParamsV1 {

    @Schema(description = "Identificador público da matrícula")
    @NotNull(message = "O uid da matrícula é obrigatório.")
    private Long uid;
}
