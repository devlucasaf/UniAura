package erp.uniaura.services.web.disciplina.pesquisarDisciplinasDoCurso.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarDisciplinasDoCursoRetornoV1 {

    @Schema(description = "Total de disciplinas do curso, ignorando a paginação")
    private Long total;

    @Schema(description = "Disciplinas da página pedida")
    private List<PesquisarDisciplinasDoCursoDisciplinaRetornoV1> lista;
}
