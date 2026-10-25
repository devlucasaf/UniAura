package erp.uniaura.services.web.turma.removerDisciplinaDaTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverDisciplinaDaTurmaParamsV1 {

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long turmaUid;

    @Schema(description = "Identificador público da disciplina a desvincular")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;
}
