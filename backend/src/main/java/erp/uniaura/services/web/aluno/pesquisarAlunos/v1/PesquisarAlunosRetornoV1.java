package erp.uniaura.services.web.aluno.pesquisarAlunos.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarAlunosRetornoV1 {

    @Schema(description = "Total de alunos que atendem ao filtro, sem considerar a paginação")
    private Long total;

    @Schema(description = "Alunos da página pedida")
    private List<PesquisarAlunosAlunoRetornoV1> lista;
}
