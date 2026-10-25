package erp.uniaura.services.web.coordenacao.definirProfessorRegenteDaTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefinirProfessorRegenteDaTurmaParamsV1 {

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long turmaUid;

    @Schema(description = "Identificador público do professor regente; sem valor, remove o regente atual")
    private Long professorUid;
}
