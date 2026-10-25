package erp.uniaura.services.web.coordenacao.alocarProfessorNaTurmaDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlocarProfessorNaTurmaDisciplinaParamsV1 {

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    @NotNull(message = "O uid do vínculo entre turma e disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Identificador público do novo professor responsável")
    @NotNull(message = "O uid do professor é obrigatório.")
    private Long professorUid;
}
