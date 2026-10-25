package erp.uniaura.services.web.responsavel.vincularAlunoAoResponsavel.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VincularAlunoAoResponsavelParamsV1 {

    @Schema(description = "Identificador público do responsável")
    @NotNull(message = "O uid do responsável é obrigatório.")
    private Long responsavelUid;

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Observação sobre o vínculo")
    @Size(max = 500, message = "A observação deve ter no máximo 500 caracteres.")
    private String observacao;
}
