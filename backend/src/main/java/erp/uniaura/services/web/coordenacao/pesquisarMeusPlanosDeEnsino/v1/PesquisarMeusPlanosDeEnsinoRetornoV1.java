package erp.uniaura.services.web.coordenacao.pesquisarMeusPlanosDeEnsino.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMeusPlanosDeEnsinoRetornoV1 {

    @Schema(description = "Total de planos do professor, sem considerar a paginação")
    private Long total;

    @Schema(description = "Planos da página pedida")
    private List<PesquisarMeusPlanosDeEnsinoPlanoRetornoV1> lista;
}
