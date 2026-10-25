package erp.uniaura.services.web.disciplina.detalharDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharDisciplinaParamsV1 {

    @Schema(description = "Identificador público da disciplina")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long uid;
}
