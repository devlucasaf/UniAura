package erp.uniaura.services.web.disciplina.removerPrerequisitoDaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverPrerequisitoDaDisciplinaParamsV1 {

    @Schema(description = "Identificador público da disciplina")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;

    @Schema(description = "Identificador público da disciplina que é o pré-requisito a desvincular")
    @NotNull(message = "O uid da disciplina pré-requisito é obrigatório.")
    private Long prerequisitoUid;
}
