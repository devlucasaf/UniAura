package erp.uniaura.services.web.turma.detalharTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalharTurmaParamsV1 {

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long uid;
}
