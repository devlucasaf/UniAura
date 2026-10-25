package erp.uniaura.services.web.turma.pesquisarAlunosDaTurma.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarAlunosDaTurmaRetornoV1 {

    @Schema(description = "Total de alunos da turma, ignorando a paginação")
    private Long total;

    @Schema(description = "Alunos da página pedida")
    private List<PesquisarAlunosDaTurmaAlunoRetornoV1> lista;
}
