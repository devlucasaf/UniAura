package erp.uniaura.services.web.matricula.pesquisarMatriculas.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMatriculasRetornoV1 {

    @Schema(description = "Total de matrículas que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Matrículas da página pedida")
    private List<PesquisarMatriculasMatriculaRetornoV1> lista;
}
