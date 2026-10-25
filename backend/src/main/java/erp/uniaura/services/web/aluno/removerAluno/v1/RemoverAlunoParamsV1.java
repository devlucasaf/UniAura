package erp.uniaura.services.web.aluno.removerAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoverAlunoParamsV1 {

    @Schema(description = "Identificador público do aluno a remover")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long uid;
}
