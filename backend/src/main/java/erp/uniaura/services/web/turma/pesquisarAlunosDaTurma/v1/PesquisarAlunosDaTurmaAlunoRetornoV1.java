package erp.uniaura.services.web.turma.pesquisarAlunosDaTurma.v1;

import erp.uniaura.persistence.entities.StatusAluno;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarAlunosDaTurmaAlunoRetornoV1 {

    @Schema(description = "Identificador público do aluno")
    private Long        uid;

    @Schema(description = "TRANSITÓRIO: id sequencial, ainda exigido pelo módulo de Aluno; sai quando ele migrar")
    private Long        id;

    private String      nome;
    private String      email;
    private String      matriculaRA;
    private LocalDate   dataIngresso;
    private StatusAluno status;
}
