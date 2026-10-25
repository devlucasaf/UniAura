package erp.uniaura.services.web.matricula.matricularAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MatricularAlunoParamsV1 {

    @Schema(description = "Identificador público do aluno")
    @NotNull(message = "O uid do aluno é obrigatório.")
    private Long alunoUid;

    @Schema(description = "Identificador público da turma")
    @NotNull(message = "O uid da turma é obrigatório.")
    private Long turmaUid;

    @Schema(description = "Data da matrícula; se vazia, usa a data de hoje")
    private LocalDate dataMatricula;

    @Schema(description = "Observações da matrícula")
    @Size(max = 1000, message = "As observações devem ter no máximo 1000 caracteres.")
    private String observacoes;
}
