package erp.uniaura.services.web.turma.pesquisarTurmas.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarTurmasRetornoV1 {

    @Schema(description = "Total de turmas que atendem aos filtros, ignorando a paginação")
    private Long total;

    @Schema(description = "Turmas da página pedida")
    private List<PesquisarTurmasTurmaRetornoV1> lista;
}
