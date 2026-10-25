package erp.uniaura.services.web.nota.consultarBoletim.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultarBoletimParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Período letivo do boletim, como cadastrado na turma (ex.: 2026.1)")
    @NotBlank(message = "O período letivo é obrigatório.")
    private String periodoLetivo;
}
