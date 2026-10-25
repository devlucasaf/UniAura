package erp.uniaura.services.web.disciplina.pesquisarDisciplinas.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarDisciplinasRetornoV1 {

    @Schema(description = "Total de disciplinas que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Disciplinas da página pedida")
    private List<PesquisarDisciplinasDisciplinaRetornoV1> lista;
}
