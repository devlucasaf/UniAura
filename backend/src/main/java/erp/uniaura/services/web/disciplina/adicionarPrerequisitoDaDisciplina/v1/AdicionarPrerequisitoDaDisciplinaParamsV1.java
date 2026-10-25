package erp.uniaura.services.web.disciplina.adicionarPrerequisitoDaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdicionarPrerequisitoDaDisciplinaParamsV1 {

    @Schema(description = "Identificador público da disciplina que passa a ter o pré-requisito")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;

    @Schema(description = "Identificador público da disciplina que será o pré-requisito")
    @NotNull(message = "O uid da disciplina pré-requisito é obrigatório.")
    private Long prerequisitoUid;
}
