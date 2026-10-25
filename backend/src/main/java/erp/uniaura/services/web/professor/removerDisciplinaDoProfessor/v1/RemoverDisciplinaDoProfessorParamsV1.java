package erp.uniaura.services.web.professor.removerDisciplinaDoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverDisciplinaDoProfessorParamsV1 {

    @Schema(description = "Identificador público do professor")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long professorUid;

    @Schema(description = "Identificador público da disciplina a desvincular")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;
}
