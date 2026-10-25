package erp.uniaura.services.web.estagio.pesquisarRelatoriosDoEstagio.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarRelatoriosDoEstagioRetornoV1 {

    @Schema(description = "Total de relatórios do estágio, ignorando a paginação")
    private Long total;

    @Schema(description = "Relatórios da página pedida")
    private List<PesquisarRelatoriosDoEstagioRelatorioRetornoV1> lista;
}
