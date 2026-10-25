package erp.uniaura.services.web.matricula.pesquisarMatriculas.v1;

import erp.uniaura.persistence.entities.StatusMatricula;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PesquisarMatriculasMatriculaRetornoV1 {

    @Schema(description = "Identificador público da matrícula")
    private Long            uid;

    @Schema(description = "Identificador público do aluno")
    private Long            alunoUid;

    private String          alunoNome;
    private String          alunoMatriculaRA;

    @Schema(description = "Identificador público da turma")
    private Long            turmaUid;

    private String          turmaCodigo;
    private String          turmaPeriodoLetivo;
    private LocalDate       dataMatricula;
    private StatusMatricula status;
}
