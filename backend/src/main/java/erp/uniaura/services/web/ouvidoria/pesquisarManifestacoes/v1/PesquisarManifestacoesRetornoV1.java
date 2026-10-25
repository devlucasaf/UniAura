package erp.uniaura.services.web.ouvidoria.pesquisarManifestacoes.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarManifestacoesRetornoV1 {

    @Schema(description = "Total de manifestações que atendem ao filtro, ignorando a paginação")
    private Long total;

    @Schema(description = "Manifestações da página pedida")
    private List<PesquisarManifestacoesManifestacaoRetornoV1> lista;
}
