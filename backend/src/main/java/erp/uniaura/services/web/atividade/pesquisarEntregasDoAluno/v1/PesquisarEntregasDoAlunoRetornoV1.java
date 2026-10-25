package erp.uniaura.services.web.atividade.pesquisarEntregasDoAluno.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEntregasDoAlunoRetornoV1 {

    @Schema(description = "Total de entregas que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Entregas da página pedida")
    private List<PesquisarEntregasDoAlunoEntregaRetornoV1> lista;
}
