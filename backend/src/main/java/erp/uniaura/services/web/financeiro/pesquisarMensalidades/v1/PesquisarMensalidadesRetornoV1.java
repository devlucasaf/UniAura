package erp.uniaura.services.web.financeiro.pesquisarMensalidades.v1;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PesquisarMensalidadesRetornoV1 {

    @Schema(description = "Total de mensalidades que atendem ao filtro, sem considerar a paginação")
    private Long total;

    @Schema(description = "Mensalidades da página pedida")
    private List<PesquisarMensalidadesMensalidadeRetornoV1> lista;
}
