package erp.uniaura.services.web.professor.adicionarDisciplinaAoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdicionarDisciplinaAoProfessorParamsV1 {

    @Schema(description = "Identificador público do professor")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long professorUid;

    @Schema(description = "Identificador público da disciplina que o professor poderá lecionar")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;
}
