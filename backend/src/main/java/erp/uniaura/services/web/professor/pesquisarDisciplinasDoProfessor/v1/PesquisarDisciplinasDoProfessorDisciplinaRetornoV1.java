package erp.uniaura.services.web.professor.pesquisarDisciplinasDoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PesquisarDisciplinasDoProfessorDisciplinaRetornoV1 {

    @Schema(description = "Identificador público do vínculo entre o professor e a disciplina")
    private Long            uid;

    @Schema(description = "Identificador público da disciplina")
    private Long            disciplinaUid;
    private String          disciplinaCodigo;
    private String          disciplinaNome;
    private LocalDateTime   criadoEm;
}
