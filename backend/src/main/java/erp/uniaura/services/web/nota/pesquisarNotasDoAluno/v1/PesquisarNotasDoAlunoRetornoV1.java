package erp.uniaura.services.web.nota.pesquisarNotasDoAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarNotasDoAlunoRetornoV1 {

    @Schema(description = "Total de notas do aluno, ignorando a paginação")
    private Long total;

    @Schema(description = "Notas da página pedida")
    private List<PesquisarNotasDoAlunoNotaRetornoV1> lista;
}
