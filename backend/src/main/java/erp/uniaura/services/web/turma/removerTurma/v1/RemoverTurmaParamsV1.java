package erp.uniaura.services.web.turma.removerTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverTurmaParamsV1 {

    @Schema(description = "Identificador público da turma a remover")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long uid;
}
