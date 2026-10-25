package erp.uniaura.services.web.coordenacao.consultarCargaHorariaDoProfessor.v1;

import erp.uniaura.persistence.entities.DiaSemana;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class ConsultarCargaHorariaDoProfessorAlocacaoRetornoV1 {

    @Schema(description = "Identificador público do vínculo entre turma e disciplina")
    private Long        turmaDisciplinaUid;
    private Long        turmaUid;
    private String      turmaCodigo;
    private String      periodoLetivo;
    private Long        disciplinaUid;
    private String      disciplinaCodigo;
    private String      disciplinaNome;
    private DiaSemana   diaSemana;
    private LocalTime   horarioInicio;
    private LocalTime   horarioFim;
}
