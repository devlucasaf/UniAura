package erp.uniaura.services.web.estagio.pesquisarEstagios.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarEstagiosRetornoV1 {

    @Schema(description = "Total de estágios que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Estágios da página pedida")
    private List<PesquisarEstagiosEstagioRetornoV1> lista;
}
