package erp.uniaura.services.web.turma.cadastrarTurma.v1;

import erp.uniaura.persistence.entities.Turno;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarTurmaParamsV1 {

    @Schema(description = "Código único da turma")
    @NotBlank(message = "O código é obrigatório.")
    @Size(max = 50, message = "O código deve ter no máximo 50 caracteres.")
    private String codigo;

    @Schema(description = "Identificador público do curso da turma")
    @NotNull(message = "O curso é obrigatório.")
    private Long cursoUid;

    @Schema(description = "Período letivo (ex.: 2026.1)")
    @NotBlank(message = "O período letivo é obrigatório.")
    @Size(max = 20, message = "O período letivo deve ter no máximo 20 caracteres.")
    private String periodoLetivo;

    @Schema(description = "Série ou semestre da turma")
    @NotBlank(message = "A série é obrigatória.")
    @Size(max = 50, message = "A série deve ter no máximo 50 caracteres.")
    private String serie;

    @Schema(description = "Sala da turma")
    @Size(max = 50, message = "A sala deve ter no máximo 50 caracteres.")
    private String sala;

    @Schema(description = "Turno da turma")
    @NotNull(message = "O turno é obrigatório.")
    private Turno turno;

    @Schema(description = "TRANSITÓRIO: id do professor regente (opcional); vira uid quando Professor migrar")
    private Long professorRegenteId;

    @Schema(description = "Quantidade máxima de alunos")
    @NotNull(message = "A capacidade máxima é obrigatória.")
    @Positive(message = "A capacidade máxima deve ser positiva.")
    private Integer capacidadeMaxima;

    @Schema(description = "Se a turma está ativa; sem valor, é cadastrada como ativa")
    private Boolean ativa;
}
