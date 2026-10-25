package erp.uniaura.services.web.frequencia.registrarChamada.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarChamadaRegistroParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Verdadeiro se o aluno esteve presente")
    @NotNull(message = "Informe se o aluno esteve presente.")
    private Boolean presente;

    @Schema(description = "Justificativa da falta; descartada quando o aluno está presente")
    @Size(max = 1000, message = "A justificativa deve ter no máximo 1000 caracteres.")
    private String justificativa;
}
