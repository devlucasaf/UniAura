package erp.uniaura.services.web.curso.pesquisarCursos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarCursosRetornoV1 {

    @Schema(description = "Total de cursos que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Cursos da página pedida")
    private List<PesquisarCursosCursoRetornoV1> lista;
}
