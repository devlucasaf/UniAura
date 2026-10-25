package erp.uniaura.services.web.professor.pesquisarDisciplinasDoProfessor.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarDisciplinasDoProfessorRetornoV1 {

    @Schema(description = "Disciplinas que o professor está habilitado a lecionar")
    private List<PesquisarDisciplinasDoProfessorDisciplinaRetornoV1> lista;
}
