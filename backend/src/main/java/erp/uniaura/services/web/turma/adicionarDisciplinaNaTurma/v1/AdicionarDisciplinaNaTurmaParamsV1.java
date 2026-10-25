package erp.uniaura.services.web.turma.adicionarDisciplinaNaTurma.v1;

import erp.uniaura.persistence.entities.DiaSemana;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AdicionarDisciplinaNaTurmaParamsV1 {

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long turmaUid;

    @Schema(description = "Identificador público da disciplina")
    @NotNull(message = "O uid da disciplina é obrigatório.")
    private Long disciplinaUid;

    @Schema(description = "TRANSITÓRIO: id do professor responsável; vira uid quando Professor migrar")
    @NotNull(message = "O professor é obrigatório.")
    private Long professorId;

    @Schema(description = "Dia da semana da aula")
    @NotNull(message = "O dia da semana é obrigatório.")
    private DiaSemana diaSemana;

    @Schema(description = "Horário de início da aula (HH:mm)")
    @NotNull(message = "O horário de início é obrigatório.")
    private LocalTime horarioInicio;

    @Schema(description = "Horário de término da aula (HH:mm), depois do início")
    @NotNull(message = "O horário de término é obrigatório.")
    private LocalTime horarioFim;
}
