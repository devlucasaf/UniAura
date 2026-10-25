package erp.uniaura.services.web.frequencia.consultarPercentualDePresenca.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarPercentualDePresencaParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Identificador público da disciplina")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;
}
