package erp.uniaura.services.web.disciplina.cadastrarDisciplina.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarDisciplinaParamsV1 {

    @Schema(description = "Código único da disciplina")
    @NotBlank(message = "O código é obrigatório.")
    @Size(max = 20, message = "O código deve ter no máximo 20 caracteres.")
    private String codigo;

    @Schema(description = "Nome da disciplina")
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.")
    private String nome;

    @Schema(description = "Ementa da disciplina")
    @Size(max = 4000, message = "A ementa deve ter no máximo 4000 caracteres.")
    private String ementa;

    @Schema(description = "Carga horária, em horas")
    @NotNull(message = "A carga horária é obrigatória.")
    @Positive(message = "A carga horária deve ser positiva.")
    private Integer cargaHoraria;

    @Schema(description = "Identificador público do curso da disciplina")
    @NotNull(message = "O curso é obrigatório.")
    private Long cursoUid;

    @Schema(description = "Período (semestre) em que a disciplina é oferecida")
    @NotNull(message = "O período é obrigatório.")
    @Positive(message = "O período deve ser positivo.")
    private Integer periodo;

    @Schema(description = "Se a disciplina está ativa; sem valor, é cadastrada como ativa")
    private Boolean ativo;
}
