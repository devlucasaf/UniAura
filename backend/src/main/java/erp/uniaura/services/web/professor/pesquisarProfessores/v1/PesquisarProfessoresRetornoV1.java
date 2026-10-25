package erp.uniaura.services.web.professor.pesquisarProfessores.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarProfessoresRetornoV1 {

    @Schema(description = "Total de professores que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Professores da página pedida")
    private List<PesquisarProfessoresProfessorRetornoV1> lista;
}
