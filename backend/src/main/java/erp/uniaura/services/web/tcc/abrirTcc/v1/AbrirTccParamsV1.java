package erp.uniaura.services.web.tcc.abrirTcc.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbrirTccParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Título do trabalho")
    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 300, message = "O título deve ter no máximo 300 caracteres.")
    private String titulo;

    @Schema(description = "Identificador público do professor orientador")
    @NotNull(message = "O professor orientador é obrigatório.")
    private Long professorOrientadorUid;

    @Schema(description = "Identificador público do professor coorientador")
    private Long professorCoorientadorUid;

    @Schema(description = "Período letivo do TCC (ex.: 2026.1)")
    @NotBlank(message = "O período letivo é obrigatório.")
    @Size(max = 10, message = "O período letivo deve ter no máximo 10 caracteres.")
    private String periodoLetivo;
}
