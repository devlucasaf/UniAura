package erp.uniaura.services.web.coordenacao.pesquisarPlanosDeEnsino.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarPlanosDeEnsinoRetornoV1 {

    @Schema(description = "Total de planos que atendem ao filtro, sem considerar a paginação")
    private Long total;

    @Schema(description = "Planos da página pedida")
    private List<PesquisarPlanosDeEnsinoPlanoRetornoV1> lista;
}
