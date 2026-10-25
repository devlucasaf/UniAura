package erp.uniaura.services.web.avaliacaoDocente.registrarAvaliacaoDocente.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrarAvaliacaoDocenteParamsV1 {

    @Schema(description = "Identificador público do vínculo entre turma e disciplina avaliado")
    @NotNull(message = "O vínculo turma/disciplina é obrigatório.")
    private Long turmaDisciplinaUid;

    @Schema(description = "Nota de didática, de 1 a 5")
    @NotNull(message = "A nota de didática é obrigatória.")
    @Min(value = 1, message = "A nota de didática deve ser de 1 a 5.")
    @Max(value = 5, message = "A nota de didática deve ser de 1 a 5.")
    private Integer notaDidatica;

    @Schema(description = "Nota de pontualidade, de 1 a 5")
    @NotNull(message = "A nota de pontualidade é obrigatória.")
    @Min(value = 1, message = "A nota de pontualidade deve ser de 1 a 5.")
    @Max(value = 5, message = "A nota de pontualidade deve ser de 1 a 5.")
    private Integer notaPontualidade;

    @Schema(description = "Nota de disponibilidade, de 1 a 5")
    @NotNull(message = "A nota de disponibilidade é obrigatória.")
    @Min(value = 1, message = "A nota de disponibilidade deve ser de 1 a 5.")
    @Max(value = 5, message = "A nota de disponibilidade deve ser de 1 a 5.")
    private Integer notaDisponibilidade;

    @Schema(description = "Comentário anônimo sobre o professor")
    @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres.")
    private String comentario;
}
