package erp.uniaura.services.web.coordenacao.pesquisarReunioesDoColegiado.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarReunioesDoColegiadoRetornoV1 {

    @Schema(description = "Total de reuniões que atendem ao filtro, sem considerar a paginação")
    private Long total;

    @Schema(description = "Reuniões da página pedida")
    private List<PesquisarReunioesDoColegiadoReuniaoRetornoV1> lista;
}
